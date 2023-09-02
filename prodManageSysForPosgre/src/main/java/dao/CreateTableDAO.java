package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateTableDAO extends DAO {

	/**
	 * テーブル作成メソッド
	 * 
	 * @param なし
	 * @return line:作成成功したテーブル数(処理数9行と等しくなる必要あり)
	 */
	@SuppressWarnings("resource")
	public int createTable() {
		int line = 0;
		String[] ExistCheckSQL = new String[9];
		ExistCheckSQL[0] = "SELECT * FROM information_schema.tables WHERE table_name = 'user_master'";
		ExistCheckSQL[1] = "SELECT * FROM information_schema.tables WHERE table_name = 'product_master'";
		ExistCheckSQL[2] = "SELECT * FROM information_schema.tables WHERE table_name = 'customer_master'";
		ExistCheckSQL[3] = "SELECT * FROM information_schema.tables WHERE table_name = 'supplier_master'";
		ExistCheckSQL[4] = "SELECT * FROM information_schema.tables WHERE table_name = 'purchase_order'";
		ExistCheckSQL[5] = "SELECT * FROM information_schema.tables WHERE table_name = 'puroduct_stock'";
		ExistCheckSQL[6] = "SELECT * FROM information_schema.tables WHERE table_name = 'entry_exit_info'";
		ExistCheckSQL[7] = "SELECT * FROM information_schema.tables WHERE table_name = 'order_table'";
		ExistCheckSQL[8] = "SELECT * FROM information_schema.tables WHERE table_name = 'amount_calc_all'";
		String[] CreateSQL = new String[9];
		CreateSQL[0] = "CREATE TABLE USER_MASTER(USER_ID VARCHAR(6)PRIMARY KEY,NAME VARCHAR(50)NOT NULL,PASSWORD VARCHAR(20)NOT NULL,DEPT VARCHAR(50)NOT NULL,ETC VARCHAR(50),HIRE_DATE VARCHAR(10)NOT NULL,REGIST_DATE VARCHAR(10)NOT NULL,REGIST_USER VARCHAR(20)NOT NULL)";
		CreateSQL[1] = "CREATE TABLE PRODUCT_MASTER(PRODUCT_NO VARCHAR(10)PRIMARY KEY,PRODUCT_NAME VARCHAR(100)NOT NULL,SUPPLIER_NO VARCHAR(6)NOT NULL,UNIT_PRICE NUMERIC(8,2)NOT NULL,SELLING_PRICE NUMERIC(8,2),LEADTIME NUMERIC(3,0)NOT NULL,LOT NUMERIC(6,0)NOT NULL,LOCATION CHAR(6),BASESTOCK NUMERIC(6,0)DEFAULT 0 NOT NULL,ETC VARCHAR(120),REGIST_DATE VARCHAR(10)NOT NULL,REGIST_USER VARCHAR(20)NOT NULL)";
		CreateSQL[2] = "CREATE TABLE CUSTOMER_MASTER(CUSTOMER_NO VARCHAR(5)PRIMARY KEY,CUSTOMER_NAME VARCHAR(100)NOT NULL,BRANCH_NAME VARCHAR(100),ZIP_NO VARCHAR(7),ADDRESS1 VARCHAR(100),ADDRESS2 VARCHAR(100),ADDRESS3 VARCHAR(100),TEL VARCHAR(15)NOT NULL,FAX VARCHAR(15),MANAGER VARCHAR(30),DELIVARY_LEADTIME NUMERIC(3,0)NOT NULL,ETC VARCHAR(120),REGIST_DATE VARCHAR(10)NOT NULL,REGIST_USER VARCHAR(20)NOT NULL)";
		CreateSQL[3] = "CREATE TABLE SUPPLIER_MASTER(SUPPLIER_NO VARCHAR(6)PRIMARY KEY,SUPPLIER_NAME VARCHAR(100)NOT NULL,BRANCH_NAME VARCHAR(100),ZIP_NO VARCHAR(7),ADDRESS1 VARCHAR(100),ADDRESS2 VARCHAR(100),ADDRESS3 VARCHAR(100),TEL VARCHAR(15)NOT NULL,FAX VARCHAR(15),MANAGER VARCHAR(30),ETC VARCHAR(120),REGIST_DATE VARCHAR(10)NOT NULL,REGIST_USER VARCHAR(20)NOT NULL)";
		CreateSQL[4] = "CREATE TABLE PURCHASE_ORDER(PO_NO VARCHAR(8)PRIMARY KEY,CUSTOMER_NO VARCHAR(5)NOT NULL,PRODUCT_NO VARCHAR(10)NOT NULL,ORDER_QTY NUMERIC(8,0)NOT NULL,DELIVERY_DATE VARCHAR(10)NOT NULL,SHIP_DATE VARCHAR(10),FIN_FLG CHAR(1)DEFAULT 0 NOT NULL,ORDER_DATE VARCHAR(10)NOT NULL,REGIST_USER VARCHAR(20)NOT NULL)";
		CreateSQL[5] = "CREATE TABLE PURODUCT_STOCK(STOCK_INFO_DATE VARCHAR(10),PRODUCT_NO VARCHAR(10),STOCK_QTY NUMERIC(8,0),T_NYUKO NUMERIC(8,0),T_SYUKO NUMERIC(8,0),T_NYUKA NUMERIC(8,0),T_SYUKA NUMERIC(8,0),UP_DATE VARCHAR(10)NOT NULL,PRIMARY KEY(STOCK_INFO_DATE,PRODUCT_NO))";
		CreateSQL[6] = "CREATE TABLE ENTRY_EXIT_INFO(EN_EX_ID VARCHAR(8)PRIMARY KEY,EN_EX_DATE VARCHAR(10)NOT NULL,PRODUCT_NO VARCHAR(10)NOT NULL,NYUKO_QTY NUMERIC(8,0),SYUKO_QTY NUMERIC(8,0),REASON VARCHAR(60)NOT NULL,REGIST_DATE VARCHAR(10)NOT NULL,REGIST_USER VARCHAR(20)NOT NULL)";
		CreateSQL[7] = "CREATE TABLE ORDER_TABLE(ORDER_NO VARCHAR(13)PRIMARY KEY,SUPPLIER_NO VARCHAR(6)NOT NULL,PRODUCT_NO VARCHAR(10)NOT NULL,ORDER_QTY NUMERIC(8,0)NOT NULL,DELIVERY_DATE VARCHAR(10)NOT NULL,BIKO VARCHAR(120),DUE_DATE VARCHAR(10),DUE_QTY NUMERIC(8,0)DEFAULT 0,FIN_FLG CHAR(1)DEFAULT 0 NOT NULL,REGIST_USER VARCHAR(20)NOT NULL,ORDER_DATE VARCHAR(10)NOT NULL,ORDER_USER VARCHAR(20)NOT NULL)";
		CreateSQL[8] = "CREATE VIEW AMOUNT_CALC_ALL(\"PRODUCT_NO\",\"PRODUCT_NAME\",\"SUPPLIER_NO\",\"SUPPLIER_NAME\",\"UNIT_PRICE\",\"LOT_PCS\",\"LEADTIME_FROM_SUPPLIER\",\"EXPECTED_DELIVERY_DATE\",\"BASESTOCK\",\"STOCK_INFO_MONTH\",\"DATE_FOR_SORT\",\"PO_NO\",\"CUSTOMER_NO\",\"LEADTIME_TO_CUSTOMER\",\"DELIVERY_DATE_TO_CUSTOMER\",\"ORDER_NO\",\"STOCK_CHANGE_DATE\",\"INCLEASE_QTY\",\"DECREASE_QTY\",\"FOR_SUM_QTY\")AS(SELECT PM.PRODUCT_NO,PM.PRODUCT_NAME,SM.SUPPLIER_NO,SM.SUPPLIER_NAME,PM.UNIT_PRICE,PM.LOT,PM.LEADTIME,TO_CHAR((CURRENT_DATE+(PM.LEADTIME*interval '1 days')),'YYYY/MM/DD'),PM.BASESTOCK,PS.STOCK_INFO_DATE,'1970/01/01',NULL,NULL,NULL,NULL,NULL,NULL,PS.STOCK_QTY,NULL,PS.STOCK_QTY FROM PRODUCT_MASTER PM INNER JOIN SUPPLIER_MASTER SM ON PM.SUPPLIER_NO=SM.SUPPLIER_NO INNER JOIN PURODUCT_STOCK PS ON PM.PRODUCT_NO=PS.PRODUCT_NO WHERE PS.STOCK_INFO_DATE=(SELECT MAX(STOCK_INFO_DATE)FROM PURODUCT_STOCK PS2 WHERE PS.PRODUCT_NO=PS2.PRODUCT_NO)UNION SELECT PM.PRODUCT_NO,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,TO_CHAR((TO_DATE(PO.DELIVERY_DATE,'YYYY/MM/DD')-(CM.DELIVARY_LEADTIME*interval'1 days')),'YYYY/MM/DD'),PO.PO_NO,CM.CUSTOMER_NO,CM.DELIVARY_LEADTIME,PO.DELIVERY_DATE,NULL,TO_CHAR((TO_DATE(PO.DELIVERY_DATE,'YYYY/MM/DD')-(CM.DELIVARY_LEADTIME*interval'1 days')),'YYYY/MM/DD'),NULL,PO.ORDER_QTY,-1*PO.ORDER_QTY FROM PRODUCT_MASTER PM INNER JOIN PURCHASE_ORDER PO ON PM.PRODUCT_NO=PO.PRODUCT_NO INNER JOIN CUSTOMER_MASTER CM ON PO.CUSTOMER_NO=CM.CUSTOMER_NO WHERE PO.FIN_FLG='0' UNION SELECT PM.PRODUCT_NO,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,OT.DELIVERY_DATE,NULL,NULL,NULL,NULL,OT.ORDER_NO,OT.DELIVERY_DATE,OT.ORDER_QTY,NULL,OT.ORDER_QTY FROM PRODUCT_MASTER PM INNER JOIN ORDER_TABLE OT ON PM.PRODUCT_NO=OT.PRODUCT_NO WHERE OT.FIN_FLG='0')";
		String[][] CommentSQL = new String[9][15];
		CommentSQL[0][0] = "COMMENT ON TABLE USER_MASTER IS 'ユーザーマスタ'";
		CommentSQL[0][1] = "COMMENT ON COLUMN USER_MASTER.USER_ID is 'ユーザーID'";
		CommentSQL[0][2] = "COMMENT ON COLUMN USER_MASTER.NAME is 'ユーザ-名'";
		CommentSQL[0][3] = "COMMENT ON COLUMN USER_MASTER.PASSWORD is 'パスワード'";
		CommentSQL[0][4] = "COMMENT ON COLUMN USER_MASTER.DEPT is '分類'";
		CommentSQL[0][5] = "COMMENT ON COLUMN USER_MASTER.ETC is '備考'";
		CommentSQL[0][6] = "COMMENT ON COLUMN USER_MASTER.HIRE_DATE is '入社日'";
		CommentSQL[0][7] = "COMMENT ON COLUMN USER_MASTER.REGIST_DATE is '登録日'";
		CommentSQL[0][8] = "COMMENT ON COLUMN USER_MASTER.REGIST_USER is '登録者'";

		CommentSQL[1][0] = "COMMENT ON TABLE PRODUCT_MASTER IS '品番マスタ'";
		CommentSQL[1][1] = "COMMENT ON COLUMN PRODUCT_MASTER.PRODUCT_NO is '品番'";
		CommentSQL[1][2] = "COMMENT ON COLUMN PRODUCT_MASTER.PRODUCT_NAME is '品名'";
		CommentSQL[1][3] = "COMMENT ON COLUMN PRODUCT_MASTER.SUPPLIER_NO is '仕入先コード'";
		CommentSQL[1][4] = "COMMENT ON COLUMN PRODUCT_MASTER.UNIT_PRICE is '仕入単価'";
		CommentSQL[1][5] = "COMMENT ON COLUMN PRODUCT_MASTER.SELLING_PRICE is '売価'";
		CommentSQL[1][6] = "COMMENT ON COLUMN PRODUCT_MASTER.LEADTIME is '購買リードタイム'";
		CommentSQL[1][7] = "COMMENT ON COLUMN PRODUCT_MASTER.LOT is '購買ロット'";
		CommentSQL[1][8] = "COMMENT ON COLUMN PRODUCT_MASTER.LOCATION is '在庫ロケーション'";
		CommentSQL[1][9] = "COMMENT ON COLUMN PRODUCT_MASTER.BASESTOCK is '基本在庫'";
		CommentSQL[1][10] = "COMMENT ON COLUMN PRODUCT_MASTER.ETC is '備考'";
		CommentSQL[1][11] = "COMMENT ON COLUMN PRODUCT_MASTER.REGIST_DATE is '登録日'";
		CommentSQL[1][12] = "COMMENT ON COLUMN PRODUCT_MASTER.REGIST_USER is '登録者'";

		CommentSQL[2][0] = "COMMENT ON TABLE CUSTOMER_MASTER IS '顧客先マスタ'";
		CommentSQL[2][1] = "COMMENT ON COLUMN CUSTOMER_MASTER.CUSTOMER_NO is '顧客コード'";
		CommentSQL[2][2] = "COMMENT ON COLUMN CUSTOMER_MASTER.CUSTOMER_NAME is '会社名'";
		CommentSQL[2][3] = "COMMENT ON COLUMN CUSTOMER_MASTER.BRANCH_NAME is '支店名'";
		CommentSQL[2][4] = "COMMENT ON COLUMN CUSTOMER_MASTER.ZIP_NO is '郵便番号'";
		CommentSQL[2][5] = "COMMENT ON COLUMN CUSTOMER_MASTER.ADDRESS1 is '住所１'";
		CommentSQL[2][6] = "COMMENT ON COLUMN CUSTOMER_MASTER.ADDRESS2 is '住所２'";
		CommentSQL[2][7] = "COMMENT ON COLUMN CUSTOMER_MASTER.ADDRESS3 is '住所３'";
		CommentSQL[2][8] = "COMMENT ON COLUMN CUSTOMER_MASTER.TEL is '電話番号'";
		CommentSQL[2][9] = "COMMENT ON COLUMN CUSTOMER_MASTER.FAX is 'FAX番号'";
		CommentSQL[2][10] = "COMMENT ON COLUMN CUSTOMER_MASTER.MANAGER is '担当者'";
		CommentSQL[2][11] = "COMMENT ON COLUMN CUSTOMER_MASTER.DELIVARY_LEADTIME is '輸送リードタイム'";
		CommentSQL[2][12] = "COMMENT ON COLUMN CUSTOMER_MASTER.ETC is '備考'";
		CommentSQL[2][13] = "COMMENT ON COLUMN CUSTOMER_MASTER.REGIST_DATE is '登録日'";
		CommentSQL[2][14] = "COMMENT ON COLUMN CUSTOMER_MASTER.REGIST_USER is '登録者'";

		CommentSQL[3][0] = "COMMENT ON TABLE SUPPLIER_MASTER IS '仕入先マスタ'";
		CommentSQL[3][1] = "COMMENT ON COLUMN SUPPLIER_MASTER.SUPPLIER_NO is '仕入先コード'";
		CommentSQL[3][2] = "COMMENT ON COLUMN SUPPLIER_MASTER.SUPPLIER_NAME is '会社名'";
		CommentSQL[3][3] = "COMMENT ON COLUMN SUPPLIER_MASTER.BRANCH_NAME is '支店名'";
		CommentSQL[3][4] = "COMMENT ON COLUMN SUPPLIER_MASTER.ZIP_NO is '郵便番号'";
		CommentSQL[3][5] = "COMMENT ON COLUMN SUPPLIER_MASTER.ADDRESS1 is '住所１'";
		CommentSQL[3][6] = "COMMENT ON COLUMN SUPPLIER_MASTER.ADDRESS2 is '住所２'";
		CommentSQL[3][7] = "COMMENT ON COLUMN SUPPLIER_MASTER.ADDRESS3 is '住所３'";
		CommentSQL[3][8] = "COMMENT ON COLUMN SUPPLIER_MASTER.TEL is '電話番号'";
		CommentSQL[3][9] = "COMMENT ON COLUMN SUPPLIER_MASTER.FAX is 'FAX番号'";
		CommentSQL[3][10] = "COMMENT ON COLUMN SUPPLIER_MASTER.MANAGER is '担当者'";
		CommentSQL[3][11] = "COMMENT ON COLUMN SUPPLIER_MASTER.ETC is '備考'";
		CommentSQL[3][12] = "COMMENT ON COLUMN SUPPLIER_MASTER.REGIST_DATE is '登録日'";
		CommentSQL[3][13] = "COMMENT ON COLUMN SUPPLIER_MASTER.REGIST_USER is '登録者'";

		CommentSQL[4][0] = "COMMENT ON TABLE PURCHASE_ORDER IS '受注テーブル'";
		CommentSQL[4][1] = "COMMENT ON COLUMN PURCHASE_ORDER.PO_NO is '受注番号'";
		CommentSQL[4][2] = "COMMENT ON COLUMN PURCHASE_ORDER.CUSTOMER_NO is '顧客先番号'";
		CommentSQL[4][3] = "COMMENT ON COLUMN PURCHASE_ORDER.PRODUCT_NO is '品番'";
		CommentSQL[4][4] = "COMMENT ON COLUMN PURCHASE_ORDER.ORDER_QTY is '注文個数'";
		CommentSQL[4][5] = "COMMENT ON COLUMN PURCHASE_ORDER.DELIVERY_DATE is '納期'";
		CommentSQL[4][6] = "COMMENT ON COLUMN PURCHASE_ORDER.SHIP_DATE is '出荷日'";
		CommentSQL[4][7] = "COMMENT ON COLUMN PURCHASE_ORDER.FIN_FLG is '完了フラグ'";
		CommentSQL[4][8] = "COMMENT ON COLUMN PURCHASE_ORDER.ORDER_DATE is '注文日'";
		CommentSQL[4][9] = "COMMENT ON COLUMN PURCHASE_ORDER.REGIST_USER is '登録者'";
		
		CommentSQL[5][0] = "COMMENT ON TABLE PURODUCT_STOCK IS '在庫テーブル';";
		CommentSQL[5][1] = "COMMENT ON COLUMN PURODUCT_STOCK.STOCK_INFO_DATE is '年月';";
		CommentSQL[5][2] = "COMMENT ON COLUMN PURODUCT_STOCK.PRODUCT_NO is '品番';";
		CommentSQL[5][3] = "COMMENT ON COLUMN PURODUCT_STOCK.STOCK_QTY is '現在在庫数';";
		CommentSQL[5][4] = "COMMENT ON COLUMN PURODUCT_STOCK.T_NYUKO is '当月入庫数';";
		CommentSQL[5][5] = "COMMENT ON COLUMN PURODUCT_STOCK.T_SYUKO is '当月出庫数';";
		CommentSQL[5][6] = "COMMENT ON COLUMN PURODUCT_STOCK.T_NYUKA is '当月入荷数';";
		CommentSQL[5][7] = "COMMENT ON COLUMN PURODUCT_STOCK.T_SYUKA is '当月出荷数';";
		CommentSQL[5][8] = "COMMENT ON COLUMN PURODUCT_STOCK.UP_DATE is '更新日';";

		CommentSQL[6][0] = "COMMENT ON TABLE ENTRY_EXIT_INFO IS '入出庫テーブル'";
		CommentSQL[6][1] = "COMMENT ON COLUMN ENTRY_EXIT_INFO.EN_EX_ID is '入出庫番号'";
		CommentSQL[6][2] = "COMMENT ON COLUMN ENTRY_EXIT_INFO.EN_EX_DATE is '日付'";
		CommentSQL[6][3] = "COMMENT ON COLUMN ENTRY_EXIT_INFO.PRODUCT_NO is '品番'";
		CommentSQL[6][4] = "COMMENT ON COLUMN ENTRY_EXIT_INFO.NYUKO_QTY is '入庫数'";
		CommentSQL[6][5] = "COMMENT ON COLUMN ENTRY_EXIT_INFO.SYUKO_QTY is '出庫数'";
		CommentSQL[6][6] = "COMMENT ON COLUMN ENTRY_EXIT_INFO.REASON is '理由'";
		CommentSQL[6][7] = "COMMENT ON COLUMN ENTRY_EXIT_INFO.REGIST_DATE is '登録日'";
		CommentSQL[6][8] = "COMMENT ON COLUMN ENTRY_EXIT_INFO.REGIST_USER is '登録者'";

		CommentSQL[7][0] = "COMMENT ON TABLE ORDER_TABLE IS '発注テーブル'";
		CommentSQL[7][1] = "COMMENT ON COLUMN ORDER_TABLE.ORDER_NO is '注文番号'";
		CommentSQL[7][2] = "COMMENT ON COLUMN ORDER_TABLE.SUPPLIER_NO is '仕入先コード'";
		CommentSQL[7][3] = "COMMENT ON COLUMN ORDER_TABLE.PRODUCT_NO is '品番'";
		CommentSQL[7][4] = "COMMENT ON COLUMN ORDER_TABLE.ORDER_QTY is '数量'";
		CommentSQL[7][5] = "COMMENT ON COLUMN ORDER_TABLE.DELIVERY_DATE is '納期'";
		CommentSQL[7][6] = "COMMENT ON COLUMN ORDER_TABLE.BIKO is '備考'";
		CommentSQL[7][7] = "COMMENT ON COLUMN ORDER_TABLE.DUE_DATE is '納入日'";
		CommentSQL[7][8] = "COMMENT ON COLUMN ORDER_TABLE.DUE_QTY is '納入数量'";
		CommentSQL[7][9] = "COMMENT ON COLUMN ORDER_TABLE.FIN_FLG is '完了フラグ'";
		CommentSQL[7][10] = "COMMENT ON COLUMN ORDER_TABLE.REGIST_USER is '登録者'";
		CommentSQL[7][11] = "COMMENT ON COLUMN ORDER_TABLE.ORDER_DATE is '発注日'";
		CommentSQL[7][12] = "COMMENT ON COLUMN ORDER_TABLE.ORDER_USER is '発注者'";
		String[] DeleteSQL = new String[9];
		DeleteSQL[0] = "DELETE FROM USER_MASTER";
		DeleteSQL[1] = "DELETE FROM PRODUCT_MASTER";
		DeleteSQL[2] = "DELETE FROM CUSTOMER_MASTER";
		DeleteSQL[3] = "DELETE FROM SUPPLIER_MASTER";
		DeleteSQL[4] = "DELETE FROM PURCHASE_ORDER";
		DeleteSQL[5] = "DELETE FROM PURODUCT_STOCK";
		DeleteSQL[6] = "DELETE FROM ENTRY_EXIT_INFO";
		DeleteSQL[7] = "DELETE FROM ORDER_TABLE";
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			ResultSet rs = null;
			for (int i = 0; i < ExistCheckSQL.length; i++) {
				st = con.prepareStatement(ExistCheckSQL[i]);
				rs = st.executeQuery();
				boolean judge = rs.next();
				if (judge == false) {
					if (CreateSQL[i] != null && !CreateSQL[i].equals("")) {
						st = con.prepareStatement(CreateSQL[i]);
						st.executeUpdate();
						for (int j = 0; j < CommentSQL[i].length; j++) {
							if (CommentSQL[i][j] != null && !CommentSQL[i][j].equals("")) {
								st = con.prepareStatement(CommentSQL[i][j]);
								st.executeUpdate();
							} else {
								break;
							}
						}
					}
				} else if (judge == true) {
					if (DeleteSQL[i] != null && !DeleteSQL[i].equals("")) {
						st = con.prepareStatement(DeleteSQL[i]);
						st.executeUpdate();
					}
				}
				// forの繰り返し回数を成功した処理回数とみなし､メソッドの戻り値とする
				line++;
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * シーケンス作成メソッド
	 * 
	 * @param なし
	 * @return line:作成成功したシーケンス数(処理数7行と等しくなる必要あり)
	 */
	@SuppressWarnings("resource")
	public int createSequence() {
		int line = 0;
		String[] ExistCheckSQL = new String[7];
		ExistCheckSQL[0] = "SELECT * FROM information_schema.\"sequences\" WHERE sequence_name = 'user_id'";
		ExistCheckSQL[1] = "SELECT * FROM information_schema.\"sequences\" WHERE sequence_name = 'product_no'";
		ExistCheckSQL[2] = "SELECT * FROM information_schema.\"sequences\" WHERE sequence_name = 'customer_no'";
		ExistCheckSQL[3] = "SELECT * FROM information_schema.\"sequences\" WHERE sequence_name = 'supplier_no'";
		ExistCheckSQL[4] = "SELECT * FROM information_schema.\"sequences\" WHERE sequence_name = 'po_no'";
		ExistCheckSQL[5] = "SELECT * FROM information_schema.\"sequences\" WHERE sequence_name = 'en_ex_id'";
		ExistCheckSQL[6] = "SELECT * FROM information_schema.\"sequences\" WHERE sequence_name = 'order_no'";

		String[] DropSequenceSQL = new String[7];
		DropSequenceSQL[0] = "DROP SEQUENCE USER_ID";
		DropSequenceSQL[1] = "DROP SEQUENCE PRODUCT_NO";
		DropSequenceSQL[2] = "DROP SEQUENCE CUSTOMER_NO";
		DropSequenceSQL[3] = "DROP SEQUENCE SUPPLIER_NO";
		DropSequenceSQL[4] = "DROP SEQUENCE PO_NO";
		DropSequenceSQL[5] = "DROP SEQUENCE EN_EX_ID";
		DropSequenceSQL[6] = "DROP SEQUENCE ORDER_NO";

		String[] CreateSequenceSQL = new String[7];
		CreateSequenceSQL[0] = "CREATE SEQUENCE USER_ID START WITH 5 INCREMENT BY 5 MINVALUE 5 MAXVALUE 999995 CYCLE CACHE 2";
		CreateSequenceSQL[1] = "CREATE SEQUENCE PRODUCT_NO START WITH 100 INCREMENT BY 8 MINVALUE 100 MAXVALUE 9999999999 CYCLE CACHE 2";
		CreateSequenceSQL[2] = "CREATE SEQUENCE CUSTOMER_NO START WITH 100 INCREMENT BY 100 MINVALUE 100 MAXVALUE 9900 CYCLE CACHE 2";
		CreateSequenceSQL[3] = "CREATE SEQUENCE SUPPLIER_NO START WITH 100 INCREMENT BY 10 MINVALUE 100 MAXVALUE 999990 CYCLE CACHE 2";
		CreateSequenceSQL[4] = "CREATE SEQUENCE PO_NO START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 99999 CYCLE CACHE 2";
		CreateSequenceSQL[5] = "CREATE SEQUENCE EN_EX_ID START WITH 10 INCREMENT BY 4 MINVALUE 10 MAXVALUE 99999999 CYCLE CACHE 2";
		CreateSequenceSQL[6] = "CREATE SEQUENCE ORDER_NO START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 99999 CYCLE CACHE 2";
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			ResultSet rs = null;
			for (int i = 0; i < DropSequenceSQL.length; i++) {
				st = con.prepareStatement(ExistCheckSQL[i]);
				rs = st.executeQuery();
				boolean judge = rs.next();
				if (judge == true) {
					st = con.prepareStatement(DropSequenceSQL[i]);
					st.executeUpdate();
					st = con.prepareStatement(ExistCheckSQL[i]);
					rs = st.executeQuery();
					judge = rs.next();
					if (judge == false) {
						st = con.prepareStatement(CreateSequenceSQL[i]);
						st.executeUpdate();
						line++;
					}
				} else if (judge == false) {
					st = con.prepareStatement(CreateSequenceSQL[i]);
					st.executeUpdate();
					line++;
				}
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * UserMasterテーブルへのレコードinsert用メソッド
	 * 
	 * @param なし
	 * @return line:作成成功したテーブル数(処理数10行と等しくなる必要あり)
	 */
	public int inserUserMasterTable() {
		int line = 0;
		String[] SQL = new String[10];
		SQL[0] = "INSERT INTO USER_MASTER VALUES('000005', '北川', 'Aaaaaa05', '総務部', NULL, '2010/04/01', '2020/04/30', '000020')";
		SQL[1] = "INSERT INTO USER_MASTER VALUES('000010', '石島', 'Aaaaaa10', '総務部', NULL, '2010/04/01', '2020/04/30', '000020')";
		SQL[2] = "INSERT INTO USER_MASTER VALUES('000015', '古賀', 'Aaaaaa15', '設計部', NULL, '2010/04/01', '2020/04/30', '000020')";
		SQL[3] = "INSERT INTO USER_MASTER VALUES('000020', '義則', 'Aaaaaa20', '設計部', NULL, '2010/04/01', '2020/04/30', '000020')";
		SQL[4] = "INSERT INTO USER_MASTER VALUES('000025', '金谷', 'Aaaaaa25', 'プロセス部', NULL, '2010/04/01', '2020/04/30', '000020')";
		SQL[5] = "INSERT INTO USER_MASTER VALUES('000030', '馬場', 'Aaaaaa30', 'プロセス部', NULL, '2010/04/01', '2020/04/30', '000020')";
		SQL[6] = "INSERT INTO USER_MASTER VALUES('000035', '西条', 'Aaaaaa35', '製造部', NULL, '2010/04/01', '2020/04/30', '000020')";
		SQL[7] = "INSERT INTO USER_MASTER VALUES('000040', '高城', 'Aaaaaa40', '製造部', NULL, '2010/04/01', '2020/04/30', '000020')";
		SQL[8] = "INSERT INTO USER_MASTER VALUES('000045', '入山', 'Aaaaaa45', '営業部', NULL, '2010/04/01', '2020/04/30', '000020')";
		SQL[9] = "INSERT INTO USER_MASTER VALUES('000050', '平井', 'Aaaaaa50', '営業部', NULL, '2010/04/01', '2020/04/30', '000020')";
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			for (int i = 0; i < SQL.length; i++) {
				st = con.prepareStatement(SQL[i]);
				line += st.executeUpdate();
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * ProductMasterテーブルへのレコードinsert用メソッド
	 * 
	 * @param なし
	 * @return line:作成成功したテーブル数(処理数8行と等しくなる必要あり)
	 */
	public int insertProductMasterTable() {
		int line = 0;
		String[] SQL = new String[8];
		SQL[0] = "INSERT INTO PRODUCT_MASTER VALUES('0000000100', 'あまおう(苺)', '000100', 40, 80, 3, 2, 'A-01-1', 50, NULL, '2020/04/30', '000020')";
		SQL[1] = "INSERT INTO PRODUCT_MASTER VALUES('0000000108', '白鳳(桃)', '000110', 60, 120, 3, 2, 'A-01-2', 50, NULL, '2020/04/30', '000020')";
		SQL[2] = "INSERT INTO PRODUCT_MASTER VALUES('0000000116', '佐藤錦(さくらんぼ)', '000120', 80, 160, 3, 2, 'A-01-3', 50, NULL, '2020/04/30', '000020')";
		SQL[3] = "INSERT INTO PRODUCT_MASTER VALUES('0000000124', 'デコポン(柑橘)', '000130', 90, 180, 3, 2, 'A-01-4', 50, NULL, '2020/04/30', '000020')";
		SQL[4] = "INSERT INTO PRODUCT_MASTER VALUES('0000000132', '巨峰(ぶどう)', '000140', 100, 200, 3, 2, 'A-01-5', 50, NULL, '2020/04/30', '000020')";
		SQL[5] = "INSERT INTO PRODUCT_MASTER VALUES('0000000140', '大根', '000150', 110, 220, 3, 2, 'B-01-1', 50, NULL, '2020/04/30', '000020')";
		SQL[6] = "INSERT INTO PRODUCT_MASTER VALUES('0000000148', '南瓜', '000160', 120, 240, 3, 2, 'B-01-2', 50, NULL, '2020/04/30', '000020')";
		SQL[7] = "INSERT INTO PRODUCT_MASTER VALUES('0000000156', '人参', '000170', 130, 260, 3, 2, 'B-01-3', 50, NULL, '2020/04/30', '000020')";
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			for (int i = 0; i < SQL.length; i++) {
				st = con.prepareStatement(SQL[i]);
				line += st.executeUpdate();
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * CustomerMasterテーブルへのレコードinsert用メソッド
	 * 
	 * @param なし
	 * @return line:作成成功した行数(処理数10行と等しくなる必要あり)
	 */
	public int insertCustomerMasterTable() {
		int line = 0;
		String[] SQL = new String[10];
		SQL[0] = "INSERT INTO CUSTOMER_MASTER VALUES('A0100', '株式会社　平和堂', '大津駅前', '5200055', '滋賀県', '大津市春日町', '1-5', '0775100371', '0775100372', '山田', 3, NULL, '2020/04/30', '000020')";
		SQL[1] = "INSERT INTO CUSTOMER_MASTER VALUES('A0200', '株式会社　平和堂', '草津駅前', '5250032', '滋賀県', '草津市大路', '一丁目10-27', '0775622755', '0775622756', '大山', 3, NULL, '2020/04/30', '000020')";
		SQL[2] = "INSERT INTO CUSTOMER_MASTER VALUES('A0300', '株式会社　平和堂', 'アルプラザ彦根', '5220074', '滋賀県', '彦根市大東町', '2-28', '0749244111', '0749244112', '萩原', 3, NULL, '2020/06/30', '000020')";
		SQL[3] = "INSERT INTO CUSTOMER_MASTER VALUES('A0400', '株式会社　フレスコ', '向陽', '5200224', '滋賀県', '大津市向陽町', '5-1', '0775711801', '0775711802', '太田', 3, NULL, '2020/04/30', '000020')";
		SQL[4] = "INSERT INTO CUSTOMER_MASTER VALUES('A0500', '株式会社　フレスコ', '能登川', '5211224', '滋賀県', '東近江市林町', '137', '0748728301', '0748728302', '吉井', 5, NULL, '2020/04/20', '000020')";
		SQL[5] = "INSERT INTO CUSTOMER_MASTER VALUES('A0600', 'スーパーマツモト', '五条', '6008811', '京都府', '京都市下京区中堂寺坊城町', '60', '0758132358', '0758132359', '酒井', 3, NULL, '2021/01/02', '000020')";
		SQL[6] = "INSERT INTO CUSTOMER_MASTER VALUES('A0700', 'スーパーマツモト', '桂川', '6018203', '京都府', '京都市南区久世地築山町', '130-1', '0759245658', '0759245659', '宮井', 3, NULL, '2020/04/30', '000020')";
		SQL[7] = "INSERT INTO CUSTOMER_MASTER VALUES('A0800', '株式会社　西友', '守山', '5240041', '滋賀県', '守山市勝部', '１丁目19-25', '0775825345', '0775825345', '北', 3, NULL, '2020/04/30', '000020')";
		SQL[8] = "INSERT INTO CUSTOMER_MASTER VALUES('A0900', '株式会社　西友', '野洲', '5202362', '滋賀県', '野洲市市三宅', '1013', '0775863601', '0775863602', '山本', 4, NULL, '2021/02/10', '000020')";
		SQL[9] = "INSERT INTO CUSTOMER_MASTER VALUES('A1000', '丸善スーパー', '守山', '5240044', '滋賀県', '守山市古高町', '388', '0775812701', '0775812702', '大野', 3, NULL, '2021/04/30', '000020')";
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			for (int i = 0; i < SQL.length; i++) {
				st = con.prepareStatement(SQL[i]);
				line += st.executeUpdate();
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * SupplierMasterテーブルへのレコードinsert用メソッド
	 * 
	 * @param なし
	 * @return line:作成成功した行数(処理数8行と等しくなる必要あり)
	 */
	public int insertSupplierMasterTable() {
		int line = 0;
		String[] SQL = new String[8];
		SQL[0] = "INSERT INTO SUPPLIER_MASTER VALUES('000100', '仕入先000100社名', '仕入先000100支店名', '0000000', '仕入先000100住所1', '仕入先000100住所2', '仕入先000100住所3', '0120909090', '0120909091', '仕入先000100担当者', NULL, '2020/04/30', '000020')";
		SQL[1] = "INSERT INTO SUPPLIER_MASTER VALUES('000110', '仕入先000110社名', '仕入先000110支店名', '9999999', '仕入先000110住所1', '仕入先000110住所2', '仕入先000110住所3', '0120787878', '0120787879', '仕入先000110担当者', NULL, '2020/04/30', '000020')";
		SQL[2] = "INSERT INTO SUPPLIER_MASTER VALUES('000120', '仕入先000120社名', '仕入先000120支店名', '8888888', '仕入先000120住所1', '仕入先000120住所2', '仕入先000120住所3', '0120565656', '0120565657', '仕入先000120担当者', NULL, '2020/04/30', '000020')";
		SQL[3] = "INSERT INTO SUPPLIER_MASTER VALUES('000130', '仕入先000130社名', '仕入先000130支店名', '7777777', '仕入先000130住所1', '仕入先000130住所2', '仕入先000130住所3', '0120343434', '0120343435', '仕入先000130担当者', NULL, '2020/04/30', '000020')";
		SQL[4] = "INSERT INTO SUPPLIER_MASTER VALUES('000140', '仕入先000140社名', '仕入先000140支店名', '6666666', '仕入先000140住所1', '仕入先000140住所2', '仕入先000140住所3', '0120121212', '0120121213', '仕入先000140担当者', NULL, '2020/04/30', '000020')";
		SQL[5] = "INSERT INTO SUPPLIER_MASTER VALUES('000150', '仕入先000150社名', '仕入先000150支店名', '5555555', '仕入先000150住所1', '仕入先000150住所2', '仕入先000150住所3', '0120990000', '0120990001', '仕入先000150担当者', NULL, '2020/04/30', '000020')";
		SQL[6] = "INSERT INTO SUPPLIER_MASTER VALUES('000160', '仕入先000160社名', '仕入先000160支店名', '4444444', '仕入先000160住所1', '仕入先000160住所2', '仕入先000160住所3', '0120778888', '0120778889', '仕入先000160担当者', NULL, '2020/04/30', '000020')";
		SQL[7] = "INSERT INTO SUPPLIER_MASTER VALUES('000170', '仕入先000170社名', '仕入先000170支店名', '3333333', '仕入先000170住所1', '仕入先000170住所2', '仕入先000170住所3', '0120556666', '0120556667', '仕入先000170担当者', NULL, '2020/04/30', '000020')";
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			for (int i = 0; i < SQL.length; i++) {
				st = con.prepareStatement(SQL[i]);
				line += st.executeUpdate();
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * PurchaseOrderテーブルへのレコードinsert用メソッド
	 * 
	 * @param なし
	 * @return line:作成成功した行数(処理数32行と等しくなる必要あり)
	 */
	public int insertPurchaseOrderTable() {
		int line = 0;
		String[] SQL = new String[32];
		SQL[0] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00001', 'A0100', '0000000100', 30, '2023/05/18', '2023/05/15', '1', '2023/05/10', '000020')";
		SQL[1] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00002', 'A0200', '0000000100', 30, '2023/06/01', '', '0', '2023/05/22', '000020')";
		SQL[2] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00003', 'A0300', '0000000100', 30, '2023/06/15', '', '0', '2023/06/07', '000020')";
		SQL[3] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00004', 'A0400', '0000000100', 30, '2023/06/29', '', '0', '2023/06/20', '000020')";
		SQL[4] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00005', 'A0500', '0000000108', 30, '2023/05/18', '2023/05/15', '1', '2023/05/10', '000020')";
		SQL[5] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00006', 'A0600', '0000000108', 30, '2023/06/01', '', '0', '2023/05/22', '000020')";
		SQL[6] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00007', 'A0700', '0000000108', 30, '2023/06/15', '', '0', '2023/06/07', '000020')";
		SQL[7] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00008', 'A0800', '0000000108', 30, '2023/06/29', '', '0', '2023/06/20', '000020')";
		SQL[8] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00009', 'A0900', '0000000116', 30, '2023/05/18', '2023/05/15', '1', '2023/05/10', '000020')";
		SQL[9] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00010', 'A1000', '0000000116', 30, '2023/06/01', '', '0', '2023/05/22', '000020')";
		SQL[10] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00011', 'A0100', '0000000116', 30, '2023/06/15', '', '0', '2023/06/07', '000020')";
		SQL[11] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00012', 'A0200', '0000000116', 30, '2023/06/29', '', '0', '2023/06/20', '000020')";
		SQL[12] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00013', 'A0300', '0000000124', 30, '2023/05/18', '2023/05/15', '1', '2023/05/10', '000020')";
		SQL[13] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00014', 'A0400', '0000000124', 30, '2023/06/01', '', '0', '2023/05/22', '000020')";
		SQL[14] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00015', 'A0500', '0000000124', 30, '2023/06/15', '', '0', '2023/06/07', '000020')";
		SQL[15] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00016', 'A0600', '0000000124', 30, '2023/06/29', '', '0', '2023/06/20', '000020')";
		SQL[16] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00017', 'A0700', '0000000132', 30, '2023/05/18', '2023/05/15', '1', '2023/05/10', '000020')";
		SQL[17] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00018', 'A0800', '0000000132', 30, '2023/06/01', '', '0', '2023/05/22', '000020')";
		SQL[18] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00019', 'A0900', '0000000132', 30, '2023/06/15', '', '0', '2023/06/07', '000020')";
		SQL[19] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00020', 'A1000', '0000000132', 30, '2023/06/29', '', '0', '2023/06/20', '000020')";
		SQL[20] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00021', 'A0100', '0000000140', 30, '2023/05/18', '2023/05/15', '1', '2023/05/10', '000020')";
		SQL[21] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00022', 'A0200', '0000000140', 30, '2023/06/01', '', '0', '2023/05/22', '000020')";
		SQL[22] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00023', 'A0300', '0000000140', 30, '2023/06/15', '', '0', '2023/06/07', '000020')";
		SQL[23] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00024', 'A0400', '0000000140', 30, '2023/06/29', '', '0', '2023/06/20', '000020')";
		SQL[24] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00025', 'A0500', '0000000148', 30, '2023/05/18', '2023/05/15', '1', '2023/05/10', '000020')";
		SQL[25] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00026', 'A0600', '0000000148', 30, '2023/06/01', '', '0', '2023/05/22', '000020')";
		SQL[26] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00027', 'A0700', '0000000148', 30, '2023/06/15', '', '0', '2023/06/07', '000020')";
		SQL[27] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00028', 'A0800', '0000000148', 30, '2023/06/29', '', '0', '2023/06/20', '000020')";
		SQL[28] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00029', 'A0900', '0000000156', 30, '2023/05/18', '2023/05/15', '1', '2023/05/10', '000020')";
		SQL[29] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00030', 'A1000', '0000000156', 30, '2023/06/01', '', '0', '2023/05/22', '000020')";
		SQL[30] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00031', 'A0100', '0000000156', 30, '2023/06/15', '', '0', '2023/06/07', '000020')";
		SQL[31] = "INSERT INTO PURCHASE_ORDER VALUES('PO-00032', 'A0200', '0000000156', 30, '2023/06/29', '', '0', '2023/06/20', '000020')";
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			for (int i = 0; i < SQL.length; i++) {
				st = con.prepareStatement(SQL[i]);
				line += st.executeUpdate();
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * PuroductStockテーブルへのレコードinsert用メソッド
	 * 
	 * @param なし
	 * @return line:作成成功した行数(処理数16行と等しくなる必要あり)
	 */
	public int insertPuroductStockTable() {
		int line = 0;
		String[] SQL = new String[16];
		SQL[0] = "INSERT INTO PURODUCT_STOCK VALUES('2023/05', '0000000100', 50, 100, 50, 100, 50, '2023/05/31')";
		SQL[1] = "INSERT INTO PURODUCT_STOCK VALUES('2023/06', '0000000100', 50, 100, 50, 110, 60, '2023/06/01')";
		SQL[2] = "INSERT INTO PURODUCT_STOCK VALUES('2022/03', '0000000108', 50, 100, 50, 100, 50, '2023/05/31')";
		SQL[3] = "INSERT INTO PURODUCT_STOCK VALUES('2022/04', '0000000108', 50, 100, 50, 110, 60, '2023/06/01')";
		SQL[4] = "INSERT INTO PURODUCT_STOCK VALUES('2023/05', '0000000116', 50, 100, 50, 100, 50, '2023/05/31')";
		SQL[5] = "INSERT INTO PURODUCT_STOCK VALUES('2023/06', '0000000116', 50, 100, 50, 110, 60, '2023/06/01')";
		SQL[6] = "INSERT INTO PURODUCT_STOCK VALUES('2022/03', '0000000124', 50, 100, 50, 100, 50, '2023/05/31')";
		SQL[7] = "INSERT INTO PURODUCT_STOCK VALUES('2022/04', '0000000124', 50, 100, 50, 110, 60, '2023/06/01')";
		SQL[8] = "INSERT INTO PURODUCT_STOCK VALUES('2023/05', '0000000132', 50, 100, 50, 100, 50, '2023/05/31')";
		SQL[9] = "INSERT INTO PURODUCT_STOCK VALUES('2023/06', '0000000132', 50, 100, 50, 110, 60, '2023/06/01')";
		SQL[10] = "INSERT INTO PURODUCT_STOCK VALUES('2022/03', '0000000140', 50, 100, 50, 100, 50, '2023/05/31')";
		SQL[11] = "INSERT INTO PURODUCT_STOCK VALUES('2022/04', '0000000140', 50, 100, 50, 110, 60, '2023/06/01')";
		SQL[12] = "INSERT INTO PURODUCT_STOCK VALUES('2023/05', '0000000148', 50, 100, 50, 100, 50, '2023/05/31')";
		SQL[13] = "INSERT INTO PURODUCT_STOCK VALUES('2023/06', '0000000148', 50, 100, 50, 110, 60, '2023/06/01')";
		SQL[14] = "INSERT INTO PURODUCT_STOCK VALUES('2022/03', '0000000156', 50, 100, 50, 100, 50, '2023/05/31')";
		SQL[15] = "INSERT INTO PURODUCT_STOCK VALUES('2022/04', '0000000156', 50, 100, 50, 110, 60, '2023/06/01')";
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			for (int i = 0; i < SQL.length; i++) {
				st = con.prepareStatement(SQL[i]);
				line += st.executeUpdate();
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * EntryExitInfoテーブルへのレコードinsert用メソッド
	 * 
	 * @param なし
	 * @return line:作成成功した行数(処理数15行と等しくなる必要あり)
	 */
	public int insertEntryExitInfoTable() {
		int line = 0;
		String[] SQL = new String[15];
		SQL[0] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000010,'2022/08/27','0000000100',40,NULL,'TEST','2023/06/27','000005')";
		SQL[1] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000014,'2022/10/16','0000000100',25,NULL,'TEST','2023/06/27','000005')";
		SQL[2] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000018,'2022/12/31','0000000100',NULL,5,'TEST','2023/06/27','000005')";
		SQL[3] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000022,'2023/01/01','0000000100',NULL,30,'TEST','2023/06/27','000005')";
		SQL[4] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000026,'2023/03/15','0000000100',NULL,10,'TEST','2023/06/27','000005')";
		SQL[5] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000030,'2023/05/24','0000000100',15,NULL,'TEST','2023/06/27','000005')";
		SQL[6] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000034,'2023/06/10','0000000100',20,NULL,'TEST','2023/06/27','000005')";
		SQL[7] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000038,'2023/06/11','0000000100',30,NULL,'TEST','2023/06/27','000005')";
		SQL[8] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000042,'2023/06/12','0000000100',NULL,25,'TEST','2023/06/27','000005')";
		SQL[9] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000046,'2023/06/13','0000000108',100,NULL,'TEST','2023/06/27','000005')";
		SQL[10] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000050,'2023/06/14','0000000108',40,NULL,'TEST','2023/06/27','000005')";
		SQL[11] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000054,'2023/06/15','0000000100',20,NULL,'TEST','2023/06/27','000005')";
		SQL[12] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000058,'2023/06/16','0000000108',NULL,10,'TEST','2023/06/27','000005')";
		SQL[13] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000062,'2023/06/17','0000000108',NULL,18,'TEST','2023/06/27','000005')";
		SQL[14] = "INSERT INTO ENTRY_EXIT_INFO VALUES(00000066,'2023/06/18','0000000108',NULL,14,'TEST','2023/06/27','000005')";
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			for (int i = 0; i < SQL.length; i++) {
				st = con.prepareStatement(SQL[i]);
				line += st.executeUpdate();
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * OrderTableテーブルへのレコードinsert用メソッド
	 * 
	 * @param なし
	 * @return line:作成成功した行数(処理数32行と等しくなる必要あり)
	 */
	public int insertOrderTable() {
		int line = 0;
		String[] SQL = new String[32];
		SQL[0] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00001', '000100', '0000000100', 30, '2023/05/16', NULL, '2023/05/16', 30, '1', '000020', '2023/05/12', '000010')";
		SQL[1] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00002', '000100', '0000000100', 30, '2023/06/01', NULL, '', 0, '0', '000020', '2023/05/26', '000010')";
		SQL[2] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00003', '000100', '0000000100', 30, '2023/06/15', NULL, '', 0, '0', '000020', '2023/06/09', '000010')";
		SQL[3] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00004', '000100', '0000000100', 30, '2023/06/30', NULL, '', 0, '0', '000020', '2023/06/23', '000010')";
		SQL[4] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00005', '000110', '0000000108', 30, '2023/05/16', NULL, '2023/05/16', 30, '1', '000020', '2023/05/12', '000010')";
		SQL[5] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00006', '000110', '0000000108', 30, '2023/06/01', NULL, '', 0, '0', '000020', '2023/05/26', '000010')";
		SQL[6] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00007', '000110', '0000000108', 30, '2023/06/15', NULL, '', 0, '0', '000020', '2023/06/09', '000010')";
		SQL[7] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00008', '000110', '0000000108', 30, '2023/06/30', NULL, '', 0, '0', '000020', '2023/06/23', '000010')";
		SQL[8] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00009', '000120', '0000000116', 30, '2023/05/16', NULL, '2023/05/16', 30, '1', '000020', '2023/05/12', '000010')";
		SQL[9] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00010', '000120', '0000000116', 30, '2023/06/01', NULL, '', 0, '0', '000020', '2023/05/26', '000010')";
		SQL[10] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00011', '000120', '0000000116', 20, '2023/06/15', NULL, '', 0, '0', '000020', '2023/06/09', '000010')";
		SQL[11] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00012', '000120', '0000000116', 40, '2023/06/30', NULL, '', 0, '0', '000020', '2023/06/23', '000010')";
		SQL[12] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00013', '000130', '0000000124', 30, '2023/05/16', NULL, '2023/05/16', 30, '1', '000020', '2023/05/12', '000010')";
		SQL[13] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00014', '000130', '0000000124', 30, '2023/06/01', NULL, '', 0, '0', '000020', '2023/05/26', '000010')";
		SQL[14] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00015', '000130', '0000000124', 20, '2023/06/15', NULL, '', 0, '0', '000020', '2023/06/09', '000010')";
		SQL[15] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00016', '000130', '0000000124', 40, '2023/06/30', NULL, '', 0, '0', '000020', '2023/06/23', '000010')";
		SQL[16] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00017', '000140', '0000000132', 30, '2023/05/16', NULL, '2023/05/16', 30, '1', '000020', '2023/05/12', '000010')";
		SQL[17] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00018', '000140', '0000000132', 30, '2023/06/01', NULL, '', 0, '0', '000020', '2023/05/26', '000010')";
		SQL[18] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00019', '000140', '0000000132', 30, '2023/06/15', NULL, '', 0, '0', '000020', '2023/06/09', '000010')";
		SQL[19] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00020', '000140', '0000000132', 20, '2023/06/30', NULL, '', 0, '0', '000020', '2023/06/23', '000010')";
		SQL[20] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00021', '000150', '0000000140', 30, '2023/05/16', NULL, '2023/05/16', 30, '1', '000020', '2023/05/12', '000010')";
		SQL[21] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00022', '000150', '0000000140', 30, '2023/06/01', NULL, '', 0, '0', '000020', '2023/05/26', '000010')";
		SQL[22] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00023', '000150', '0000000140', 30, '2023/06/15', NULL, '', 0, '0', '000020', '2023/06/09', '000010')";
		SQL[23] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00024', '000150', '0000000140', 20, '2023/06/30', NULL, '', 0, '0', '000020', '2023/06/23', '000010')";
		SQL[24] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00025', '000160', '0000000148', 30, '2023/05/16', NULL, '2023/05/16', 30, '1', '000020', '2023/05/12', '000010')";
		SQL[25] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00026', '000160', '0000000148', 20, '2023/06/01', NULL, '', 0, '0', '000020', '2023/05/26', '000010')";
		SQL[26] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00027', '000160', '0000000148', 20, '2023/06/15', NULL, '', 0, '0', '000020', '2023/06/09', '000010')";
		SQL[27] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00028', '000160', '0000000148', 20, '2023/06/30', NULL, '', 0, '0', '000020', '2023/06/23', '000010')";
		SQL[28] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00029', '000170', '0000000156', 30, '2023/05/16', NULL, '2023/05/16', 30, '1', '000020', '2023/05/12', '000010')";
		SQL[29] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00030', '000170', '0000000156', 20, '2023/06/01', NULL, '', 0, '0', '000020', '2023/05/26', '000010')";
		SQL[30] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00031', '000170', '0000000156', 20, '2023/06/15', NULL, '', 0, '0', '000020', '2023/06/09', '000010')";
		SQL[31] = "INSERT INTO ORDER_TABLE VALUES('OD-2305-00032', '000170', '0000000156', 20, '2023/06/30', NULL, '', 0, '0', '000020', '2023/06/23', '000010')";
		try {
			Connection con = getConnection();
			PreparedStatement st = null;
			for (int i = 0; i < SQL.length; i++) {
				st = con.prepareStatement(SQL[i]);
				line += st.executeUpdate();
			}
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("SQLでエラーが発生しました。");
			e.printStackTrace();
		}
		return line;
	}
}
