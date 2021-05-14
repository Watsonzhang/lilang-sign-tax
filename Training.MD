###*关于培训使用财报模板转化：*
####一、背景：
 　　使用我们系统申报的客户越来越多,而客户使用的财务软件不同和会计准则的不同导致客户三大财务报表结构各不相同,
 定义一层适配层来转换客户各自不同的财务报表数据就成为了迫切需要。
 #####１.需求实现思路
    　　借鉴了现有的章鱼报表系统,在章鱼报表里存放了不同租户的转化模板,例如利郎与秀域的财报转换模板，
    当采集客户端或者导入功能录入了客户的报表时,获取章鱼上的转换模板,模板中每个cell中存放的是租户报表的具体坐标.
    可查看具体操作图,当读取了租户数据和转化模板后，我们可以得出每个坐标具体该取的值，批量获取数据后，即可以录入我司系统.
    客户财务报表数据流到我们系统后，客户或者实施就可以根据录入的数据来完成申报了.
##### 2.接入流程：
    ①确定采集方案,导入或者采集 
    ②租户提供标准化的报表样本,该报表必须是标准化的,若客户租户多个子公司报表结果不同,需引导统一成统一结构
    ③在章鱼报表上配置租户的模板转化.
    ④数据流入结合转化模板录入系统。
#####3.存在问题：
    上述中的③决定这取数的对错，也是整个环节中最重要的部分，目前由研发配置,但是研发很难接触到客户的真实数据,
    以及财务相关知识的欠缺，导致现在有问题都是由实施反馈给研发哪个坐标有问题,然后再找研发改公式.研发还得结合真实数据和咨询财务知识来修改公式。
    
####二、实操步骤
[章鱼报表入口](http://report.yunzhangfang.com:8080/rm/systemManagement/system)

 ####1.创建客户财报模板 
  模板入口页面
![模板入口](https://static.dingtalk.com/media/lALPDhYBP3eN3ILNA_fNByg_1832_1015.png?auth_bizType=IM&auth_bizEntity=%7B%22cid%22%3A%224248001%3A206645299%22%2C%22msgId%22%3A%227018321049717%22%7D&bizType=im&open_id=206645299)

  创建模板页面
![创建模板](https://static.dingtalk.com/media/lALPDh0cPME53u_NA7bNBy8_1839_950.png?auth_bizType=IM&auth_bizEntity=%7B%22cid%22%3A%224248001%3A206645299%22%2C%22msgId%22%3A%226998688112021%22%7D&bizType=im&open_id=206645299)

  导入基础模板页面
![导入基础模板](https://static.dingtalk.com/media/lALPDgQ9xj_LgrzNA7nNBzY_1846_953.png?auth_bizType=IM&auth_bizEntity=%7B%22cid%22%3A%224248001%3A206645299%22%2C%22msgId%22%3A%227016321847873%22%7D&bizType=im&open_id=206645299)
 
 ####2.编辑模板 填入对应坐标的公式
![编辑模板](https://static.dingtalk.com/media/lALPDgQ9xj-22-7NA57NBzc_1847_926.png?auth_bizType=IM&auth_bizEntity=%7B%22cid%22%3A%224248001%3A206645299%22%2C%22msgId%22%3A%227004832946484%22%7D&bizType=im&open_id=206645299)
 
 ####3.保存模板
![保存模板](https://static.dingtalk.com/media/lALPDhYBP3edaojNA7TNBzo_1850_948.png?auth_bizType=IM&auth_bizEntity=%7B%22cid%22%3A%224248001%3A206645299%22%2C%22msgId%22%3A%227016273627309%22%7D&bizType=im&open_id=206645299)
 
 ####4.导入或者重新采集 查看数据
   导入客户标准化后的excel
![导入excel数据](https://static.dingtalk.com/media/lALPDhYBP3bBaCfNA2bNBs8_1743_870.png?auth_bizType=IM&auth_bizEntity=%7B%22cid%22%3A%224248001%3A206645299%22%2C%22msgId%22%3A%227003600904116%22%7D&bizType=im&open_id=206645299)
   采集客户标准化后的数据
![采集界面](https://static.dingtalk.com/media/lALPDhJzwNLs9V_NA6vNBzY_1846_939.png?auth_bizType=IM&auth_bizEntity=%7B%22cid%22%3A%224248001%3A206645299%22%2C%22msgId%22%3A%227004944575513%22%7D&bizType=im&open_id=206645299)
   查看采集或者导入后的数据
![导入后或者采集后的数据](https://static.dingtalk.com/media/lALPDgtYw4wkBV3NA4rNByg_1832_906.png?auth_bizType=IM&auth_bizEntity=%7B%22cid%22%3A%224248001%3A206645299%22%2C%22msgId%22%3A%227001568256785%22%7D&bizType=im&open_id=206645299)

####三、内置公式说明 
 ```
CURRENT():获取到期报表数据
LAST():获取上期报表数据
IF(a,b,c): 若a为真则填入b 否则填入c
支持加减乘除的数学运算，可由符号+ 结合符号- 实现减 例如+(-a)
```
案例：
![秀域报表修改案例](https://static.dingtalk.com/media/lALPDhmOviAtsN_NA2HNBzI_1842_865.png?auth_bizType=IM&auth_bizEntity=%7B%22cid%22%3A%224248001%3A206645299%22%2C%22msgId%22%3A%227002576036513%22%7D&bizType=im&open_id=206645299)

####四、与申报表的关系与区别
    



