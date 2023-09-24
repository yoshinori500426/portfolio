package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import bean.AmountCalcAll;
import bean.OrderTable;
import bean.ProductMaster;

public class AmountCalcDAO extends DAO {
	/**
	 * VIEW ｢AMOUNT_CALC_ALL｣参照メソッド
	 * ProductMasterテーブルの情報(PRODUCT_NO)を元にしたテーブルの情報(PRODUCT_NO)毎のレコード参照メソッド
	 * 全PRODUCT_NOの検索を行うメソッド
	 *
	 * Runnnableインターフェイスのメソッド｢run()｣は､引数を持てない為､
	 * AmountCalcActionDAOのメソッドに必要なインスタンス｢request｣をフィールド経由で渡そうとしたが、
	 * インスタンス｢request｣は渡せたが、「request.getSession();」でセッションのインスタンスが取得できない不具合発生
	 * その為、セッションのインスタンスをRunnnableインターフェイスのメソッド｢run()｣へ渡す事とする
	 * それに合わせ、メソッドの引数を「HttpServletRequest request」から、「HttpSession session」へ変更する
	 *
	 * @param HttpSession session
	 * @return judgeProsess 「3：成功､発注/納期短縮が必要な品番あり」
	 * @return judgeProsess 「2：成功､発注/納期短縮が必要な品番なし」
	 * @return judgeProsess 「1：キャンセル(｢キャンセル｣ボタン押下)」
	 * @return judgeProsess 「0：失敗」
	 */
	public int searchAllByOrdNo(HttpSession session) {
		// 正常終了/異常終了の判定用変数
		int judgeProsess = 3;
		// レコード内容判定用変数
		boolean judge = true;
		// プログレスバー用カウント変数
		double countForProductNo = 0;
		// レコード用カウント変数
		int countForRecord = 0;
		// 在庫数の累計用
		int cumulativeQty = 0;
		// 基本在庫数格納用
		int basestock = 0;
		// 戻り値用の変数(リスト型)を宣言
		Map<String, List<AmountCalcAll>> amountCalcAllListMap = null;
		// Mapに代入するリストを宣言
		List<AmountCalcAll> amountCalcAllList = null;
		// リストに格納するbeanの宣言
		AmountCalcAll amountCalcAll = null;
		// テーブル｢ProductMaster｣から､｢PRODUCT_NO｣リスト取得
		List<ProductMaster> productMasterList = searchAll();
		int productNoNum = productMasterList.size();
		// 例外「java.util.ConcurrentModificationException」回避の為、
		// リスト「productMasterList」中の対象｢PRODUCT_NO｣を格納するリストを宣言
		// →拡張For文で、リスト要素の削除動作を行わせると、例外「java.util.ConcurrentModificationException」が投げられる
		List<ProductMaster> productMasterListFinal = null;

		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			ResultSet rs = null;
			// スレッド割り込み時､例外を投げた際､正常に終了させる為の細工
			session.setAttribute("con", con);

			LoopBreak: for (ProductMaster productMaster : productMasterList) {
				// 状況出力
				countForProductNo++;
				outPutMSG(session, "処理中｡", String.valueOf((int) (countForProductNo / productNoNum * 100)),
						"progress-bar-info", "1");
				// データベース情報取得
				st = con.prepareStatement("SELECT ACA.* , SUM(\"FOR_SUM_QTY\") OVER (ORDER BY \"DATE_FOR_SORT\", \"PO_NO\", \"ORDER_NO\" ASC) CUMULATIVE_QTY "
											+ "FROM AMOUNT_CALC_ALL ACA WHERE \"PRODUCT_NO\" = ? ORDER BY \"DATE_FOR_SORT\" ASC");
				st.setString(1, productMaster.getProductNo());
				// スレッド割り込み時､例外を投げた際､正常に終了させる為の細工
				session.setAttribute("st", st);
				rs = st.executeQuery();
				judge = true;
				amountCalcAllList = null;
				amountCalcAll = null;
				countForRecord = 0;
				cumulativeQty = 0;
				basestock = 0;
				amountCalcAllList = new ArrayList<>();
				while (rs.next()) {
					countForRecord++;
					amountCalcAll = new AmountCalcAll();
					amountCalcAll.setProductNo(rs.getString("PRODUCT_NO"));
					amountCalcAll.setProductName(rs.getString("PRODUCT_NAME"));
					amountCalcAll.setSupplierNo(rs.getString("SUPPLIER_NO"));
					amountCalcAll.setSupplierName(rs.getString("SUPPLIER_NAME"));
					amountCalcAll.setUnitPrice(rs.getInt("UNIT_PRICE"));
					amountCalcAll.setLotPcs(rs.getInt("LOT_PCS"));
					amountCalcAll.setLeadtimeFromSupplier(rs.getInt("LEADTIME_FROM_SUPPLIER"));
					amountCalcAll.setExpectedDeliveryDate(rs.getString("EXPECTED_DELIVERY_DATE"));
					amountCalcAll.setBasestock(rs.getInt("BASESTOCK"));
					amountCalcAll.setStockInfoMonth(rs.getString("STOCK_INFO_MONTH"));
					amountCalcAll.setDateForSort(rs.getString("DATE_FOR_SORT"));
					amountCalcAll.setPoNo(rs.getString("PO_NO"));
					amountCalcAll.setCustomerNo(rs.getString("CUSTOMER_NO"));
					amountCalcAll.setLeadtimeToCustomer(rs.getInt("LEADTIME_TO_CUSTOMER"));
					amountCalcAll.setDeliveryDateToCustomer(rs.getString("DELIVERY_DATE_TO_CUSTOMER"));
					amountCalcAll.setOrderNo(rs.getString("ORDER_NO"));
					amountCalcAll.setStockChangeDate(rs.getString("STOCK_CHANGE_DATE"));
					amountCalcAll.setIncleaseQty(rs.getInt("INCLEASE_QTY"));
					amountCalcAll.setDecreaseQty(rs.getInt("DECREASE_QTY"));
					amountCalcAll.setForSumQty(rs.getInt("FOR_SUM_QTY"));
					amountCalcAll.setCumulativeQty(rs.getInt("CUMULATIVE_QTY"));
					// 基本在庫数を変数｢basestock｣へ格納
					// →View｢AMOUNT_CALC_ALL｣では､基本在庫数は､各品番の1行目のみに格納されている
					if (countForRecord == 1) {
						basestock = amountCalcAll.getBasestock();
						// フィールド｢orderFinFlg｣は､
						// 「1：アクション(発注/納期調整)不要」
						// 「0-2：発注が必要な品番」
						// 「0-1：納期調整が必要な品番」
						// →仮で｢1｣を格納する
						// →品番に対するインスタンス｢amountCalcAll｣完成後に､｢0-2｣｢0-1｣に該当するか評価する
						amountCalcAll.setOrderFinFlg("1");
					}
					// 処理している｢PRODUCT_NO｣が､在庫テーブル(PURODUCT_STOCK)にない場合の処理
					if ((countForRecord == 1) && (rs.getString("STOCK_INFO_MONTH")) == null) {
						outPutMSG(session,
								"テーブル｢PURODUCT_STOCK｣に､品目｢" + productMaster.getProductNo()
										+ "｣がありません｡ <br>&emsp;&emsp;&emsp;&emsp;&emsp;管理者に連絡して下さい､処理を終了します｡",
								String.valueOf((int) (countForProductNo / productNoNum * 100)), "progress-bar-danger",
								"0");
						judgeProsess = 0;
						break LoopBreak;
					}
					// 累計が基本在庫を切る場合の処理
					// →最終的に在庫見通しが､基本在庫以上になる場合でも､一時的にでも基本在庫を下回る様であれば､
					// 納期短縮が必要なため､異常と規定する
					if (basestock > rs.getInt("CUMULATIVE_QTY")) {
						judge = false;
					}
					// 列｢CUMULATIVE_QTY｣の最終行の値を取る為の操作
					cumulativeQty = rs.getInt("CUMULATIVE_QTY");
					// ProductNo固定のリストへレコードを加える
					amountCalcAllList.add(amountCalcAll);
					// キャンセル時(スレッドのインスタンスのメソッド「.interrupt()」が実行された場合)の処理
					// キャンセルを行う為、セッション属性に保存指定している
					// クラス「AmountCalcDAO」で動作中のスレッドクラスのインスタンス「thread」を取得
					Thread thread = (Thread) session.getAttribute("therad");
					if (thread.isInterrupted()) {
						// 状況出力
						outPutMSG(session, "キャンセルします｡", String.valueOf((int) (countForProductNo / productNoNum * 100)),
								"progress-bar-success", "0");
						judgeProsess = 1;
						break LoopBreak;
					}
				}
				// 処理を行った｢PRODUCT_NO｣が､発注処理/納期短縮対応に該当する場合(異常状態)
				// →処理を行った｢PRODUCT_NO｣が､発注処理/納期短縮対応に該当しない場合(正常状態)は処理なし
				if (amountCalcAllList != null && amountCalcAll != null && judge == false) {
					// productMasterListFinalがNULLの場合､インスタンスを作成
					if (productMasterListFinal == null) {
						productMasterListFinal = new ArrayList<ProductMaster>();
					}
					// amountCalcAllListMapがNULLの場合､インスタンスを作成
					if (amountCalcAllListMap == null) {
						amountCalcAllListMap = new HashMap<String, List<AmountCalcAll>>();
					}
					// 各｢PRODUCT_NO｣の1行目の処理
					// →発注操作の終了フラグへの処理
					// →品番に対するインスタンス｢amountCalcAll｣作成時､仮の値として｢1｣を入力している
					// →最終確認を行う
					if (basestock > cumulativeQty) {
						// フィールド｢orderFinFlg｣は､
						// 「1：アクション(発注/納期調整)不要」
						// 「0-2：発注が必要な品番」
						// 「0-1：納期調整が必要な品番」
						amountCalcAllList.get(0).setOrderFinFlg("0-2");
					} else {
						amountCalcAllList.get(0).setOrderFinFlg("0-1");
					}
					// productMasterListFinalへ､処理を行ったインスタンス(productMaster)を追加
					productMasterListFinal.add(productMaster);
					// 列｢CUMULATIVE_QTY｣の最終行の値を､amountCalcAllListの1行目の｢latestCumulativeQty｣に代入
					amountCalcAllList.get(0).setLatestCumulativeQty(cumulativeQty);
					// Mapへ､キー｢PRODUCT_NO｣､値｢AmountCalcAllList｣を追加
					amountCalcAllListMap.put(productMaster.getProductNo(), amountCalcAllList);
				}
				// 動作確認を目的に、処理を3秒止める
				// →この処理を入れることで､try-catch文のcatch箇所に割り込み時の動作を記述する必要が生じる
				Thread.sleep(1000);
			}
			// 正常終了/異常終了の判定
			if (countForProductNo == productNoNum) {
				if (amountCalcAllListMap == null && productMasterListFinal == null) {
					// 状況出力
					outPutMSG(session, "正常に終了しました｡ 発注/納期短縮が必要な品番はありません｡", "100", "progress-bar-success", "2");
					judgeProsess = 2;
				} else {
					// 状況出力
					outPutMSG(session, "正常に終了しました｡ 発注/納期短縮が必要な品番があります｡", "100", "progress-bar-success", "3");
					judgeProsess = 3;
				}
				// 保存属性の変更
				// →amountCalcAllListMap/amountCalcAllList/productMasterList
				new AmountCalcDAO().changeAttribute(session, amountCalcAllListMap, productMasterListFinal);
			} else {
				//メッセージ「"テーブル｢PURODUCT_STOCK｣に､品目｢" + productMaster.getProductNo()+ "｣がありません｡ 管理者に連絡して下さい､処理を終了します｡"」を表示させる為の条件
				if ((String) session.getAttribute("amountCalcMSG") == null) {
					outPutMSG(session, "処理が異常終了しました｡", String.valueOf((int) (countForProductNo / productNoNum * 100)), "progress-bar-danger", "0");
				}
				// 保存属性のnullクリア
				// →amountCalcAllListMap/amountCalcAllList/productMasterList
				new AmountCalcDAO().changeAttribute(session, null, null);
				judgeProsess = 0;
			}
			st.close();
			con.close();
		} catch (InterruptedException e) {
			// スレッド割り込み時､例外を投げた際､正常に終了させる為の細工
			Connection con = (Connection) session.getAttribute("con");
			PreparedStatement st = (PreparedStatement) session.getAttribute("st");
			try {
				st.close();
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// キャンセル時(=スレッド割り込み時)の動作
			// 「Thread.sleep(5000);」を記述していると、割り込みで例外「InterruptedException」が投げられる
			// その際、この記述の動作を行う
			// 状況出力
			outPutMSG(session, "キャンセルします｡", String.valueOf((int) (countForProductNo / productNoNum * 100)), "progress-bar-success", "0");
			judgeProsess = 1;
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return judgeProsess;
	}

	/**
	 * VIEW ｢AMOUNT_CALC_ALL｣参照メソッド OrderTableへの発注処理後の状態を取得する為のメソッド
	 * 発注処理を行ったPRODUCT_NOのみ､検索を行うメソッド 戻り値は､メソッド｢searchAllByOrdNo()｣と揃えている
	 * ->このメソッドでは､「1：キャンセル(｢キャンセル｣ボタン押下)」は発生しない
	 *
	 * Runnnableインターフェイスのメソッド｢run()｣は､引数を持てない為､
	 * AmountCalcActionDAOのメソッドに必要なインスタンス｢request｣をフィールド経由で渡そうとしたが、
	 * インスタンス｢request｣は渡せたが、渡したインスタンス｢request｣から「request.getSession();」でセッションのインスタンスが取得できない不具合発生
	 * その為、セッションのインスタンスをRunnnableインターフェイスのメソッド｢run()｣へ渡す事とする
	 * それに合わせ、メソッドの引数を「HttpServletRequest request」から、「HttpSession session」へ変更する
	 *
	 * @param HttpSession session
	 * @return judgeProsess 「3：成功､発注/納期短縮が必要な品番あり」
	 * @return judgeProsess 「2：成功､発注/納期短縮が必要な品番なし」
	 * @return judgeProsess 「1：キャンセル(｢キャンセル｣ボタン押下)」
	 * @return judgeProsess 「0：失敗」
	 */
	@SuppressWarnings("unchecked")
	public int searchByOrdNo(HttpSession session, OrderTable ot) {
		// 正常終了/異常終了の判定用変数
		int judgeProsess = 3;
		// レコード内容判定用変数
		boolean judge = true;
		// レコード用カウント変数
		int countForRecord = 0;
		// 在庫数の累計用
		int cumulativeQty = 0;
		// 基本在庫数格納用
		int basestock = 0;
		// 発注処理を行った｢PRODUCT_NO｣取得
		String productNo = ot.getProductNo();
		// セッション属性に保存しているMap｢amountCalcAllListMap｣取得
		// →取得した対象｢PRODUCT_NO｣のListは､最終､このMap｢amountCalcAllListMap｣のキー｢PRODUCT_NO｣の値を置き換える
		Map<String, List<AmountCalcAll>> amountCalcAllListMap = (Map<String, List<AmountCalcAll>>) session.getAttribute("amountCalcAllListMap");
		// セッション属性に保存しているList｢productMasterList｣取得
		List<ProductMaster> productMasterListFinal = (List<ProductMaster>) session.getAttribute("productMasterListFinal");
		// Mapに代入する､対象｢PRODUCT_NO｣情報を格納するリストを宣言
		List<AmountCalcAll> amountCalcAllList = null;
		// 使用するbeanの宣言
		AmountCalcAll amountCalcAll = null;

		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			ResultSet rs = null;
			// データベース情報取得
			st = con.prepareStatement("SELECT ACA.* , SUM(\"FOR_SUM_QTY\") OVER (ORDER BY \"DATE_FOR_SORT\", \"PO_NO\", \"ORDER_NO\" ASC) CUMULATIVE_QTY "
										+ "FROM AMOUNT_CALC_ALL ACA WHERE \"PRODUCT_NO\" = ? ORDER BY \"DATE_FOR_SORT\" ASC");
			st.setString(1, productNo);
			rs = st.executeQuery();
			cumulativeQty = 0;
			amountCalcAllList = new ArrayList<>();
			while (rs.next()) {
				countForRecord++;
				amountCalcAll = new AmountCalcAll();
				amountCalcAll.setProductNo(rs.getString("PRODUCT_NO"));
				amountCalcAll.setProductName(rs.getString("PRODUCT_NAME"));
				amountCalcAll.setSupplierNo(rs.getString("SUPPLIER_NO"));
				amountCalcAll.setSupplierName(rs.getString("SUPPLIER_NAME"));
				amountCalcAll.setUnitPrice(rs.getInt("UNIT_PRICE"));
				amountCalcAll.setLotPcs(rs.getInt("LOT_PCS"));
				amountCalcAll.setLeadtimeFromSupplier(rs.getInt("LEADTIME_FROM_SUPPLIER"));
				amountCalcAll.setExpectedDeliveryDate(rs.getString("EXPECTED_DELIVERY_DATE"));
				amountCalcAll.setBasestock(rs.getInt("BASESTOCK"));
				amountCalcAll.setStockInfoMonth(rs.getString("STOCK_INFO_MONTH"));
				amountCalcAll.setDateForSort(rs.getString("DATE_FOR_SORT"));
				amountCalcAll.setPoNo(rs.getString("PO_NO"));
				amountCalcAll.setCustomerNo(rs.getString("CUSTOMER_NO"));
				amountCalcAll.setLeadtimeToCustomer(rs.getInt("LEADTIME_TO_CUSTOMER"));
				amountCalcAll.setDeliveryDateToCustomer(rs.getString("DELIVERY_DATE_TO_CUSTOMER"));
				amountCalcAll.setOrderNo(rs.getString("ORDER_NO"));
				amountCalcAll.setStockChangeDate(rs.getString("STOCK_CHANGE_DATE"));
				amountCalcAll.setIncleaseQty(rs.getInt("INCLEASE_QTY"));
				amountCalcAll.setDecreaseQty(rs.getInt("DECREASE_QTY"));
				amountCalcAll.setForSumQty(rs.getInt("FOR_SUM_QTY"));
				amountCalcAll.setCumulativeQty(rs.getInt("CUMULATIVE_QTY"));
				// 基本在庫数を変数｢basestock｣へ格納
				// →基本在庫数は､各品番の1行目のみに格納されている
				if (countForRecord == 1) {
					basestock = amountCalcAll.getBasestock();
					// フィールド｢orderFinFlg｣は､
					// 「1：アクション(発注/納期調整)不要」
					// 「0-2：発注が必要な品番」
					// 「0-1：納期調整が必要な品番」
					// →仮で｢1｣を格納する
					// →品番に対するインスタンス｢amountCalcAll｣完成後に､｢0-2｣｢0-1｣に該当するか評価する
					amountCalcAll.setOrderFinFlg("1");
				}
				// 累計が基本在庫を切る場合の処理
				// →最終的に在庫見通しが､基本在庫以上になる場合でも､一時的にでも基本在庫を下回る様であれば､
				// 納期短縮が必要なため､異常と規定する
				if (basestock > rs.getInt("CUMULATIVE_QTY")) {
					judge = false;
				}
				// 列｢CUMULATIVE_QTY｣の最終行の値を取る為の操作
				cumulativeQty = rs.getInt("CUMULATIVE_QTY");
				// ProductNo固定のリストへレコードを加える
				amountCalcAllList.add(amountCalcAll);
			}
			// 処理を行った｢PRODUCT_NO｣が､発注処理/納期短縮対応に該当する場合(異常状態)
			// →処理を行った｢PRODUCT_NO｣が､発注処理/納期短縮対応に該当しない場合(正常状態)は処理なし
			if (amountCalcAllList != null && amountCalcAll != null) {
				if (judge == false) {
					// 各｢PRODUCT_NO｣の1行目の処理
					// →発注操作の終了フラグへの処理
					// →品番に対するインスタンス｢amountCalcAll｣作成時､仮の値として｢1｣を入力している
					// →最終確認を行う
					if (basestock > cumulativeQty) {
						// フィールド｢orderFinFlg｣は､
						// 「1：アクション(発注/納期調整)不要」
						// 「0-2：発注が必要な品番」
						// 「0-1：納期調整が必要な品番」
						amountCalcAllList.get(0).setOrderFinFlg("0-2");
						// 状況出力
						outPutMSG(session, "発注処理が正常に終了しました｡ 在庫手配状態が不適切です｡", null, null, null);
						judgeProsess = 3;
					} else {
						amountCalcAllList.get(0).setOrderFinFlg("0-1");
						// 状況出力
						outPutMSG(session, "発注処理が正常に終了しました｡ 在庫手配状態は適正です｡", null, null, null);
						judgeProsess = 3;
					}
				} else if (judge == true) {
					amountCalcAllList.get(0).setOrderFinFlg("1");
					// 状況出力
					outPutMSG(session, "発注処理が正常に終了しました｡ 在庫手配状態は適正です｡", null, null, null);
					judgeProsess = 2;
				}
				// 列｢CUMULATIVE_QTY｣の最終行の値を､amountCalcAllListの1行目の｢latestCumulativeQty｣に代入
				amountCalcAllList.get(0).setLatestCumulativeQty(cumulativeQty);
				// Mapへ､キー｢PRODUCT_NO｣の値｢AmountCalcAllList｣をこの検索結果の内容に置き換える
				amountCalcAllListMap.put(productNo, amountCalcAllList);
				// 保存属性の変更
				// →amountCalcAllListMap/amountCalcAllList/productMasterList
				new AmountCalcDAO().changeAttribute(session, amountCalcAllListMap, productMasterListFinal);
			} else {
				// 状況出力
				outPutMSG(session, "処理が異常終了しました｡", null, null, null);
				judgeProsess = 0;
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return judgeProsess;
	}

	/**
	 * ProductMasterテーブル取得メソッド(検索条件なし) →PRODUCT_NOリスト取得用メソッド
	 *
	 * @param 引数無し
	 * @return productMasterList 「null：失敗」「null以外：成功」
	 */
	private List<ProductMaster> searchAll() {
		// 戻り値用の変数(リスト型)を宣言
		List<ProductMaster> productMasterList = new ArrayList<>();
		try {
			Connection con = getConnection();

			PreparedStatement st;
			st = con.prepareStatement("SELECT * FROM PRODUCT_MASTER ORDER BY PRODUCT_NO ASC");
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				ProductMaster productMaster = new ProductMaster();
				productMaster.setProductNo(rs.getString("PRODUCT_NO"));
				productMasterList.add(productMaster);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return productMasterList;
	}

	/**
	 * AmountCalcで使用する表示用セッション属性の変更メソッド
	 *
	 * @param 引数無し
	 * @return productMasterList 「null：失敗」「null以外：成功」
	 */
	public void outPutMSG(HttpSession session, String string, String string2, String string3, String string4) {
		// メッセージ変更
		session.setAttribute("amountCalcMSG", string);
		session.setAttribute("amountCalcProgPercent", string2);
		session.setAttribute("amountCalcProgColor", string3);
		// 「amountCalcProgFlg」は、
		// ●amountCalc.jsp
		// 0:途中終了、1:処理中、2:異常なし終了、3:異常あり終了
		session.setAttribute("amountCalcProgFlg", string4);
	}

	/**
	 * AmountCalcで使用するデータ用セッション属性の変更メソッド
	 *
	 * @param ｢request｣､｢amountCalcAllListMap｣｢amountCalcAllList｣｢productMasterList｣に代入するインスタンス
	 * @return 無し
	 */
	public void changeAttribute(HttpSession session, Object object, Object object2) {
		// 保存属性変更
		session.setAttribute("amountCalcAllListMap", object);
		session.setAttribute("productMasterListFinal", object2);
	}
}
