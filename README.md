# spring boot

## URL
http://localhost:8080/base-spring/login
テストユーザー: miu / miu (開発用)

## 構成

Spring Boot + Doma2
DB: postgres


## Doma2設定

アノテーションプロセッサを有効にする。
・doma-core-2.44.3.jar
・doma-processor-2.44.3.jar

Entityのプロパティには,lombokを使わない方針
(相性悪いらしいのと、実際効かなかったため)


## ログイン認証

APIなどで独自にやりたい場合は、以下に実装する。
CustomeAuthenticationProvider.java


## 権限設定

権限名は以下を参照
・enum: UserRoleName
・DB: user_roleテーブルに格納


## SQL migartion

一応ディレクトリを作っているが、migrationツールは特に導入していません。
