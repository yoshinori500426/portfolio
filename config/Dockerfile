# マルチステージビルドを利用し､Linux(ubuntu)/Tomcatの2つのイメージを使用している

# ========================================================================================================================================
# Linux(ubuntu)イメージを使用し､Javaホルダから､WARファイルを作成する
FROM ubuntu:mantic-20230624 as forMakeWAR

# drinkSaleSysForPosgre
# コンテナ内に処理用ディレクトリ作成
WORKDIR /usr/src/drinkSaleSysForPosgre/.classpath
WORKDIR /usr/src/drinkSaleSysForPosgre/.project
WORKDIR /usr/src/drinkSaleSysForPosgre/.settings
WORKDIR /usr/src/drinkSaleSysForPosgre/build
WORKDIR /usr/src/drinkSaleSysForPosgre/src
# コンテナ内の処理用ディレクトリに､GihHubディレクトリのファイルをコピー
COPY ./drinkSaleSysForPosgre/.classpath /usr/src/drinkSaleSysForPosgre/.classpath/
COPY ./drinkSaleSysForPosgre/.project /usr/src/drinkSaleSysForPosgre/.project/
COPY ./drinkSaleSysForPosgre/.settings /usr/src/drinkSaleSysForPosgre/.settings/
COPY ./drinkSaleSysForPosgre/build /usr/src/drinkSaleSysForPosgre/build/
COPY ./drinkSaleSysForPosgre/src /usr/src/drinkSaleSysForPosgre/src/
# コンテナ内の処理用ディレクトリを､WARファイルのディレクトリ構成(classファイル/ホルダ名/改装)に変更
RUN cp -r /usr/src/drinkSaleSysForPosgre/build/classes /usr/src/drinkSaleSysForPosgre/src/main/webapp/WEB-INF/
RUN mv /usr/src/drinkSaleSysForPosgre/src/main/webapp /usr/src/drinkSaleSysForPosgre/src/main/drinkSaleSysForPosgre
RUN mv /usr/src/drinkSaleSysForPosgre/src/main/drinkSaleSysForPosgre /

# prodManageSysForPosgre
# コンテナ内に処理用ディレクトリ作成
WORKDIR /usr/src/prodManageSysForPosgre/.classpath
WORKDIR /usr/src/prodManageSysForPosgre/.project
WORKDIR /usr/src/prodManageSysForPosgre/.settings
WORKDIR /usr/src/prodManageSysForPosgre/build
WORKDIR /usr/src/prodManageSysForPosgre/src
# コンテナ内の処理用ディレクトリに､GihHubディレクトリのファイルをコピー
COPY ./prodManageSysForPosgre/.classpath /usr/src/prodManageSysForPosgre/.classpath/
COPY ./prodManageSysForPosgre/.project /usr/src/prodManageSysForPosgre/.project/
COPY ./prodManageSysForPosgre/.settings /usr/src/prodManageSysForPosgre/.settings/
COPY ./prodManageSysForPosgre/build /usr/src/prodManageSysForPosgre/build/
COPY ./prodManageSysForPosgre/src /usr/src/prodManageSysForPosgre/src/
# コンテナ内の処理用ディレクトリを､WARファイルのディレクトリ構成(classファイル/ホルダ名/改装)に変更
RUN cp -r /usr/src/prodManageSysForPosgre/build/classes /usr/src/prodManageSysForPosgre/src/main/webapp/WEB-INF/
RUN mv /usr/src/prodManageSysForPosgre/src/main/webapp /usr/src/prodManageSysForPosgre/src/main/prodManageSysForPosgre
RUN mv /usr/src/prodManageSysForPosgre/src/main/prodManageSysForPosgre /

# Javaホルダ圧縮(WARファイル作成)の為､zipコマンドをインストール
RUN apt update && apt install -y zip unzip
# ディレクトリ｢drinkSaleSysForPosgre｣をzipファイルに圧縮し､拡張子をzipからwarに変更
RUN zip -r /drinkSaleSysForPosgre.zip /drinkSaleSysForPosgre
RUN mv /drinkSaleSysForPosgre.zip /drinkSaleSysForPosgre.war
# ディレクトリ｢prodManageSysForPosgre｣をzipファイルに圧縮し､拡張子をzipからwarに変更
RUN zip -r /prodManageSysForPosgre.zip /prodManageSysForPosgre
RUN mv /prodManageSysForPosgre.zip /prodManageSysForPosgre.war

# ========================================================================================================================================
# Linux(ubuntu)イメージで作成したwarファイルのデプロイ

# イメージ｢tomcat｣を使用する場合の処理
# Linux(ubuntu)イメージで作成したwarファイルをイメージ｢tomcat｣のコンテナにデプロイする
FROM tomcat:8.5
# 以下､イメージ｢tomee｣から生成されるコンテナ｢Tomcat｣のTomcatトップページが表示されない不具合対応
RUN cd $CATALINA_HOME/
RUN mv webapps webapps2
RUN mv webapps.dist webapps
# 以下､イメージ｢tomcat｣から生成されるコンテナ｢Tomcat｣の管理マネージャ(Tomcat Manager Application)操作用ファイル(設定ファイル)のコピー
COPY ./config/settings.xml $CATALINA_HOME/conf/
COPY ./config/tomcat-users.xml $CATALINA_HOME/conf/
# 以下､Linux(ubuntu)イメージで作成したwarファイルのサーブレットコンテナへの配備
COPY --from=forMakeWAR /drinkSaleSysForPosgre.war $CATALINA_HOME/webapps/
COPY --from=forMakeWAR /prodManageSysForPosgre.war $CATALINA_HOME/webapps/


# COPY ./drinkSaleSysForPosgre/drinkSaleSysForPosgre.war $CATALINA_HOME/webapps/
# COPY ./prodManageSysForPosgre/prodManageSysForPosgre.war $CATALINA_HOME/webapps/



# # イメージ｢tomee｣を使用する場合の処理
# # Linux(ubuntu)イメージで作成したwarファイルをイメージ｢tomee｣のコンテナにデプロイする
# FROM tomee:webprofile
# # 以下､イメージ｢tomee｣から生成されるコンテナ｢Tomcat｣の管理マネージャ(Tomcat Manager Application)操作用ファイル(設定ファイル)のコピー
# COPY ./config/settings.xml /usr/local/tomee/conf
# COPY ./config/tomcat-users.xml /usr/local/tomee/conf
# # 以下､Linux(ubuntu)イメージで作成したwarファイルのサーブレットコンテナへの配備
# COPY --from=forMakeWAR /drinkSaleSysForPosgre.war /usr/local/tomee/webapps/
# COPY --from=forMakeWAR /prodManageSysForPosgre.war /usr/local/tomee/webapps/


