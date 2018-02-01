# 用openssl、keytool创建数字证书颁发二级证书



### 创建一个自签名的CA证书：

* **创建一个RSA私钥，4096位，用DES3算法保护：**

```openssl genrsa -des3 -out ca.key 4096```

按要求输入所需信息

* **生成一个自签名的CA证书，有效期3650天：**

```openssl req -new -x509 -days 3650 -key ca.key -out ca.crt```

ca.crt就是一个自签名的数字证书，可以作为我们自己的CA来使用。

* **crt证书转化为cer证书**

```openssl x509 -in ca.crt -out ca.cer -outform der```



### 创建一个用CA证书签发的数字证书：

* **创建一个RSA私钥，4096位，无密码保护：**

```openssl genrsa -out server.key 4096```

* **生成一个证书请求：**

```openssl req -new -key server.key -out server.csr```

* **用CA证书来签发这个请求，有效期3650天，并得到证书：**

```openssl x509 -req -days 3650 -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out server.crt```

* **也可以用 ca 命令结合config文件来签发证书，这应该是更正式的方法：**

```openssl ca -config openssl.cnf -in server.csr -out server.crt```

可以看到，命令执行过程中提示签发证书并更新数据库文件(serial 和 index.txt)。

命令完成后，数据库文件得到更新。

server.crt就是由我们自己的CA签发的数字证书



### 使用keytool制作jks并用ca签发证书

*  **制作jks**

```keytool -genkeypair -alias server -keyalg RSA -keystore server.jks -validity 36500  ```

* **生成请求证书**

```keytool -certreq -alias server -file baidu.csr -keystore server.jks  ```

* **使用上面的ca签发证书，得到server.cer**
* **导回签发后的证书**

1. 导入信任的CA根证书到keystore

```keytool -importcert -v -trustcacerts  -alias ca_root -file ca.cer -storepass 123456 -keystore server.jks```

2. 把CA签名后的server端证书导入keystore

```keytool -importcert -v -alias server -file server.cer -storepass 123456 -keystore server.jks```

3. 查看server端证书

``` keytool -list -v -keystore server.jks  ```



### 如果ca是jks格式，如何签发证书

* **用CA证书来签发这个请求，有效期3650天，并得到证书：**

```keytool -gencert -alias rootca -keystore ca.jks -infile server.csr -outfile server.cer -validity 36500```

