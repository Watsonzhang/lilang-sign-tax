企业完税凭证和完税报表下载接口
-----------

### 1. 企业完税凭证和完税报表下载接口

#### 接口功能

> 获取制企业完税凭证和完税报表下载链接

#### URL

> [https://oapi.yunzhangfang.com/api/tax/archive/v1/download?access_token=ACCESS_TOKEN](https://oapi.yunzhangfang.com/api/tax/archive/v1/download?access_token=ACCESS_TOKEN)

#### 支持格式

> JSON

#### HTTP请求方式

> POST

#### 请求参数

|参数|必选|类型|说明|
|:----- |:-------|:-----|----- |
|taxNo |ture |string|纳税人识别号 |
|kjnd |true |int |会计年度例如：2021|
|kjqj |true |int |会计区间例如：5|
|accessKey |true |string |密钥|
|sign |true |string |签名|
|timestamp |true |string |时间戳|

#### 返回字段

|返回字段|字段类型|说明 |
|:----- |:------|:----------------------------- |
|code | string |结果标识。0：正常 其他：发生异常。 |
|message | string | 提示信息 |
|result | json |结果集 |
##### 结果集具体字段
|返回字段|字段类型|说明 |
|:----- |:------|:----------------------------- |
|url | string |下载地址 |
|data | string | 提示信息 |
|result | json |结果集 |       


#### 接口示例

> 地址：[http://www.api.com/index.php?name=可口可乐&type=1](http://www.api.com/index.php?name=可口可乐&type=1)

{
"state": 0,
"company": "可口可乐",
"category": "饮料"
}
 
 
 1.生成token /getToken（OPEN-PLATFORM-BFF应用） 
 参数 {
      "accessKey": "string",
      "accessSecret": "string"
    }
 2.生成参数
  2.1完税证明和完税报表
  例子：
  {"accessKey":"MJ3MnWdk616uVqII","kjnd":2020,"kjqj":11,"sign":"4180c38799fefd7b450b29c38eb32ef297bf5fa7320cd4393d8f7785426c329d","taxNo":"91330104341787788Y","timestamp":1620279747977} 
  2.2 税金明细表
  {"accessKey":"MJ3MnWdk616uVqII","kjnd":2021,"kjqj":3,"sign":"a79f5b6b2bbe03eb96d9ddeb8a64d82dd30b2a6e0560c56149d1b1d6feb9d982","timestamp":1620279874599}  
  [参数生成服务](http://172.22.9.161:9191/swagger-ui.html)  
  
  