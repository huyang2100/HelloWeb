package com.yang;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.org.bjca.mssp.mssg.base.MSSGResponse;
import cn.org.bjca.mssp.mssg.base.MSSPSignGroupResult;
import cn.org.bjca.mssp.mssg.client.*;
import cn.org.bjca.mssp.mssg.platform.bean.CertBean;
import cn.org.bjca.mssp.mssg.platform.sdk.CommonClientConstant;
import cn.org.bjca.mssp.mssg.platform.sdk.message.ClientSignBean;
import cn.org.bjca.mssp.mssg.platform.sdk.message.CloudRespMessage;
import cn.org.bjca.mssp.mssg.platform.sdk.message.MSSGUserMobile;
import cn.org.bjca.mssp.mssg.platform.sdk.message.ReqMessage;
import cn.org.bjca.mssp.mssg.platform.sdk.message.SignAuthInfo;
import cn.org.bjca.mssp.mssg.platform.sdk.message.SignRequest;
import cn.org.bjca.mssp.mssg.platform.sdk.message.SignSealResult;
import cn.org.bjca.mssp.mssg.platform.sdk.message.UserInfoMessage;
import cn.org.bjca.mssp.util.CipherUtil;
import cn.org.bjca.mssp.util.EncodeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 云签名网关接口用例
 * 
 * @author semper_fidelis
 *
 */
public class MssgSignClientTest {
	MSSGClientMul signClient;

	// 参数---------------------BETA环境
//	static String channelId = "CHN_9F29CD8CAABB489DB0AA20E58918871A";
//	// 应用编号 lee应用2
//	static String appId = "APP_76016F104DD7423BB98D1E56B133CB29";
//	static String algo_SHA1withRSA = "SHA1withRSA";
//	static String algo_SHA256withRSA = "SHA256withRSA";
//	static String algo_SM3withSM2 = "SM3withSM2";
//	static String dialgo_SHA1 = "SHA1";
//	static String dialgo_SHA256 = "SHA256";
//	static String dialgo_SM3 = "SM3";
	// 个人用户信息
	// static String
	// static String
	// msspId="c9af8b4d1e846b4d299767c4a2bc06412728c98a1099888e3105df314b92d41c";
	// static String msspId =
	// "c17e9a0df0db444958e4a67772a6a09dfc49a9aef52aff339e4d609e3fd76057";
	static String msspId = "c758ebec77f6619c7df9ef5f870d9fb776aa49f0b3894acf924bd15ca3db2ea0";
	static String cert_rsa_authorize = "MIIDMzCCAhugAwIBAgIJIBA1AAAAIXmQMA0GCSqGSIb3DQEBBQUAMDExCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMRMwEQYDVQQDDAptc3NwdGVzdGNhMB4XDTE4MDIyNjA4MTEyOFoXDTIzMDIyNjA5MTEyOFowcDELMAkGA1UEBgwCQ04xDzANBgNVBAMMBuW8oOS4iTFQME4GCgmSJomT8ixkAQEMQGM5YWY4YjRkMWU4NDZiNGQyOTk3NjdjNGEyYmMwNjQxMjcyOGM5OGExMDk5ODg4ZTMxMDVkZjMxNGI5MmQ0MWMwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAO+6JscOi6rRgh6Ys8kdIPjbdKsUYj4XmoLSL+sZ7BwvvgADvjo1O8Y1KMlzYSxcm2ofevGa7ZSzK0oKT7yH5HsU8vZNSHgMxndX/AGjISA4p4PidSTiKe3x5q0Uk8N4nwCwciEZkRGtE7R7ZmeYwpxw1VQKDUuQOiNU/4DUuciDAgMBAAGjgZIwgY8wHwYDVR0jBBgwFoAUfgaZPuO42ZCfGehz3Oc2ZlUYtuMwHQYDVR0OBBYEFH1WkwwHUh/PBs+yMoS3CtQCxynlMAsGA1UdDwQEAwIGwDBABgNVHSAEOTA3MDUGCSqBHIbvMgICAzAoMCYGCCsGAQUFBwIBFhpodHRwOi8vd3d3LmJqY2Eub3JnLmNuL2NwczANBgkqhkiG9w0BAQUFAAOCAQEArk6dsnah5llLLwSyiho8RWXOpmcOrx5U2Wp4nKKULutgq6Vk1NoRec2I5gBgPCEAyO6gW2hK5PbxLQZbrT13uF0Ov59cQluERdywfS/qHR6BZ2CEAEywONqNzdJKIRI2GrDBEVlrR4PZAutVlUAUmDjXVMNMvHcWwWC1oMRVcEH5epV2lgZSHZy0m8/2eDN1c+HneqwNVifi9ek2QOgaNnHPB5jfkjIrZw5o0NQMJylMiaSfqpJWSSeyjVr5CqPq5GTFtsMW34PgtC1tVYwtW9JvZ7tcx7TfHp9rM5Yzc7E00W+jrdK68GwZWMTe4vmCQcYB69wOHyUvyuwkzWq1zA==";
	static String cert_sm2_authorize = "MIICKjCCAdGgAwIBAgIJIBA0AAAAI9ITMAoGCCqBHM9VAYN1MDExCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMRMwEQYDVQQDDAptc3NwdGVzdGNhMB4XDTE4MDIyNjA4MTEyOFoXDTIzMDIyNjA5MTEyOFowcDELMAkGA1UEBgwCQ04xDzANBgNVBAMMBuW8oOS4iTFQME4GCgmSJomT8ixkAQEMQGM5YWY4YjRkMWU4NDZiNGQyOTk3NjdjNGEyYmMwNjQxMjcyOGM5OGExMDk5ODg4ZTMxMDVkZjMxNGI5MmQ0MWMwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAARc/a3sT67NbZOnm24DKLiPcLDKNXHrUTybT1O+6qm4Z66PBNuKgDGtzElww/7SlxDeKd6A8MOGFyuMgvUNx3iio4GSMIGPMB8GA1UdIwQYMBaAFAPp1JzWlhwVTUzsiWBccmZ+cO+/MB0GA1UdDgQWBBRCDEF0WmrUtu5Jo79O+51dU0dqfjALBgNVHQ8EBAMCBsAwQAYDVR0gBDkwNzA1BgkqgRyG7zICAgMwKDAmBggrBgEFBQcCARYaaHR0cDovL3d3dy5iamNhLm9yZy5jbi9jcHMwCgYIKoEcz1UBg3UDRwAwRAIgKzHSqPzQY/0FttbigGdiIE5m1B5ha1BieKeGNoN0WRgCIH5KaJiV1oswyT/V7UxhP0HUsNROYwlfvODx8ktsFrWl";
	static String cert_rsa = "MIIDMzCCAhugAwIBAgIJIBA1AAAAIXpwMA0GCSqGSIb3DQEBBQUAMDExCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMRMwEQYDVQQDDAptc3NwdGVzdGNhMB4XDTE4MDMxMjAxMTczNVoXDTIzMDMxMjAyMTczNVowcDELMAkGA1UEBgwCQ04xDzANBgNVBAMMBuW8oOS4iTFQME4GCgmSJomT8ixkAQEMQGM5YWY4YjRkMWU4NDZiNGQyOTk3NjdjNGEyYmMwNjQxMjcyOGM5OGExMDk5ODg4ZTMxMDVkZjMxNGI5MmQ0MWMwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAM58LsZBVwll9RR187rwdM93CJP/83xCaZQYF6fGhzNU0gYQun55I2Se4JXVojLZJNoUCCEgNVcUZWw5oOo2zE/yv/N84dj/Hz+yG/kRqhPiFw/42ViyQRpNmrwLd72xP26k4oUEGhEMlMM+TY/cCUJzu0LfyEBc7u8nqSVfdjs5AgMBAAGjgZIwgY8wHwYDVR0jBBgwFoAUfgaZPuO42ZCfGehz3Oc2ZlUYtuMwHQYDVR0OBBYEFN8oYPdAF+Oj5h2T+629Z9jsbIMJMAsGA1UdDwQEAwIGwDBABgNVHSAEOTA3MDUGCSqBHIbvMgICAzAoMCYGCCsGAQUFBwIBFhpodHRwOi8vd3d3LmJqY2Eub3JnLmNuL2NwczANBgkqhkiG9w0BAQUFAAOCAQEAC4HT62zGmbLLi5B3Cw4P5HgmBvsZKIi0NzL1dUe283TvOZi1beJFs85rry44nOVAxi9EdIGgMNTFUHrqmq01h43zuTzVLrzjFCC4juWFVtyb0T/eIMde406rt3Z6AQVGb/IrPFsif9PZ+gLPpMnYfjLUHaHx0DhBZWLVJPCRQmj3daT/6SQPMMZ3dcCajvG9/depV8sFiRkELVRXbsw4xc03o4wp+OF0w4hcemjoZLo5Evwbxv81xRZGF6y+PFcds43yY/oR9m/5ZA52QQL1tcFjhvoRvbtt/coUdzGCIBAELkrwz++flg0K2wi/rNdxZLhMj28nIZJgD7csM+C/MQ==";
	static String cert_sm2 = "MIICKzCCAdGgAwIBAgIJIBA0AAAAI9LkMAoGCCqBHM9VAYN1MDExCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMRMwEQYDVQQDDAptc3NwdGVzdGNhMB4XDTE4MDMxMjAxMTczNVoXDTIzMDMxMjAyMTczNVowcDELMAkGA1UEBgwCQ04xDzANBgNVBAMMBuW8oOS4iTFQME4GCgmSJomT8ixkAQEMQGM5YWY4YjRkMWU4NDZiNGQyOTk3NjdjNGEyYmMwNjQxMjcyOGM5OGExMDk5ODg4ZTMxMDVkZjMxNGI5MmQ0MWMwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAASOsi63wgkr9xSq3+W6F2TUnVzURrrbi0sMUYnUfN6nMUw+AdinYTpnlgLxZwzzLepxfONbsDcF1YZ8GPdVJUVFo4GSMIGPMB8GA1UdIwQYMBaAFAPp1JzWlhwVTUzsiWBccmZ+cO+/MB0GA1UdDgQWBBS/Ch5En2nk2kbf5BZEfxKh/AGk2zALBgNVHQ8EBAMCBsAwQAYDVR0gBDkwNzA1BgkqgRyG7zICAgMwKDAmBggrBgEFBQcCARYaaHR0cDovL3d3dy5iamNhLm9yZy5jbi9jcHMwCgYIKoEcz1UBg3UDSAAwRQIhAPL7b+hjLmD080JRzg78JFJvX0kNoPJWpadHgEfc34dQAiB/Ynakt+RPBDKtmVVo91cZxItdB551IwEnT9cXkO3QrA==";
	static String plain_data = "line";
	// 企业用户信息
	static String keyId = "ENA_80044b2f-f859-452c-9e1d-116c6f588287";

	// 参数---------------------DEV环境
	 static String channelId = "CHN_69120806E7D44CE1";
	 static String appId="67f6c99b74484c2b8bcb35473850cdd7";//北京CA演示联调Demo
	 static String algo_SHA1withRSA="SHA1withRSA";
	 static String algo_SHA256withRSA="SHA256withRSA";
	 static String algo_SM3withSM2="SM3withSM2";
	 static String dialgo_SHA1="SHA1";
	 static String dialgo_SHA256="SHA256";
	 static String dialgo_SM3="SM3";
	// //个人用户信息
	// static String
	// msspId="c9af8b4d1e846b4d299767c4a2bc06412728c98a1099888e3105df314b92d41c";
	// static String cert_rsa_authorize="";
	// static String cert_sm2_authorize="";
	// static String cert_rsa="";
	// static String cert_sm2="";
	// static String plain_data="line";

	@Before
	public void init() throws Exception {

		// url
//		 String url = "http://lctest.isignet.cn:13001";
//		 beta
//		String url = "http://beta.isignet.cn:13001";
		// dev
		 String url = "http://lctest.isignet.cn:13001";
		// 获取client连接
		signClient = MSSGClientMul.getInstance(url, appId);
		// 连接服务器超时时间，单位是毫秒，默认为3分钟
		// instance.setTimeOut(3000000);
		// 服务器响应时间
		// instance.setRespTimeout(500000);
	}

	/**
	 * 添加用户 可带扩展项
	 * 
	 * @Description: idType：SF-身份证；GA-港澳通行证；HK-户口；HZ-护照；JG-军官证；JI-警官证；JL-；SB-；SG-；WZ-；XJ-学籍；ANONYMOUS-匿名
	 * @throws Exception
	 */
	// msspId:c758ebec77f6619c7df9ef5f870d9fb776aa49f0b3894acf924bd15ca3db2ea0
	// authCode:ef304905
	// userQrCode:{"operType":"ActiveUser","version":"1.0","data":"ef304905"}
	@Test
	public void addTrustUserV2() throws Exception {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			// map.put("2.16.840.1.113732.2", "张三110101199003077432");
			AddUserResult res = signClient.addTrustUserV2("李响", "SF", "110103197606290957", "18701582100", null, null,
					map);
			System.out.println("msspId:" + res.getMsspId());
			System.out.println("authCode:" + res.getAuthCode());
			System.out.println("userQrCode:" + res.getUserQrCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询个人信息及状态， 使用msspId查 或者使用姓名、证件类型、证件号三个参数查
	 * 
	 * @throws Exception
	 */
	@Test
	public void getUserInfoAndState() throws Exception {
		try {
//			UserInfoMessage userInfo = signClient.getUserInfoAndState(msspId, "", "", "");
			 UserInfoMessage userInfo = signClient.getUserInfoAndState(null, "李响", "SF",
			 "110103197606290957");
			System.out.println("userState：" + userInfo.getUserState());
			System.out.println("msspId：" + userInfo.getKeyID());
			System.out.println("mobile：" + userInfo.getMobilePhone());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查询用户信息
	 * 
	 * @throws Exception
	 */
	@Test
	public void getUserInfo() throws Exception {
		try {
			UserInfoMessage user = signClient.getUserInfo(msspId);
			System.out.println("username：" + user.getUserName());
			System.out.println("mobile：" + user.getMobilePhone());
			System.out.println("userseal：" + user.getUserSeal().getSealImg());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询个人设备信息
	 * 
	 * @throws Exception
	 */
	@Test
	public void queryUserDevice() throws Exception {
		try {
			List<MSSGUserMobile> deviceList = signClient.queryUserDevice(appId, msspId);
			for (MSSGUserMobile mssgUserMobile : deviceList) {
				System.out.println("deviceId=" + mssgUserMobile.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询设备证书
	 * 
	 * @throws Exception
	 */
	@Test
	public void queryUserDeviceCert() throws Exception {
		try {
			List<CertBean> certBeanList = signClient.queryUserDeviceCert(appId, msspId, "IMEI1");
			for (CertBean certBean : certBeanList) {
				System.out.println("cert=" + certBean.getCert());
				System.out.println("signAlgoName=" + certBean.getSigAlgName());
				System.out.println("useage=" + certBean.getUseage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改用户手机号
	 * 
	 * @throws Exception
	 */
	@Test
	public void changeUserMobile() throws Exception {
		try {
			boolean res = signClient.changeUserMobile(msspId, "18888818888");
			System.out.println("changeUserMobile result==" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改用户状态 冻结/解冻用户
	 * 
	 * @throws Exception
	 */
	@Test
	public void changeUserStatus() throws Exception {
		try {
			boolean res = signClient.changeUserStatus(msspId, CommonClientConstant.USER_STATUS_FREE);
			System.out.println("changeUserStatus result==" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新用户个人印章图片
	 * 
	 * @throws Exception
	 */
	@Test
	public void updateUserSeal() throws Exception {
		try {
			String userSeal = "R0lGODlheAA8APcAAP4FBf4GBv4YGP47O/5QUP6Vlf7W1v7r6/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v////7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v////7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v////7+/v7+/v7+/v///yH5BAlkAPAALAAAAAB4ADwAAAj+AOEJHEiwoMGDCBMqXMiwocOHECNKnEixosWLGDNq3Mixo8ePIEOKHEmypMmTKFOqXMmypcuXMGPKnEmzps2bOHPq3Mmzp8+fQIMKHUq0J4CiMo8aBKA0ZNORTJ92lCqQqceoWJ1GrUpV49GtVZ2WVGq1q0WsVgmm/bhWK1mzGanCjTvXK9e6F8G2BYkWLMe9V80CrtgX78a+bPcOnihXJFq1U9M+XWyXb1PDZ7kOJCuWJGd4j+muvYxZYmmKhU87vHxQNUTXDB+z5gu6teOxlL36rX0bd27Guze7hW16M/GIvzEmVy51OeHjP6EjnU69uvXr2LNr3869u/fv4MMUix9Pvrz58+jTq1/Pvr379/APBgQAOw==";
			signClient.updateUserSeal(msspId, userSeal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改手机用户pin码 初次设置旧pin传空
	 * 
	 * @throws Exception
	 */
	@Test
	public void updateUserAuthCertPin() throws Exception {
		try {
			String oldEncPin = "";
			String newEncPin = "888888";
			int result = signClient.updateUserAuthCertPin(msspId, oldEncPin, newEncPin);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除用户
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteUser() throws Exception {
		try {
			boolean res = signClient.deleteUser("2eeb95480f33f15b4042c9f03ec89744c3e3c08c8596b1e0390008853d93d18b");
			System.out.println("changeUserDelete result==" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注销用户设备
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteUserDeviceCert() throws Exception {
		try {
			signClient.deleteUserDeviceCert(appId, "---msspId----", "IMEI1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * P10申请事件证书
	 * 
	 * @Description:
	 * @throws Exception
	 */
	@Test
	public void requestOneCert() throws Exception {
		try {
			OneCertRequest request = new OneCertRequest();
			request.setP10(
					"MIHsMIGTAgEAMDMxFTATBgNVBAMMDOa1i+ivleS8geS4mjENMAsGA1UECwwETVNTUDELMAkGA1UEBhMCQ04wWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAASwS3tzmklL8WWjwqfjFV6j7fcocHdpTHh28+Kz1PGG0ay8WGYq5FbqvToOOE0iP8FBOzHdZZWOLsMF9qplczEUMAoGCCqBHM9VAYN1A0gAMEUCIQDn0hnYDwNC8oaVUdrsNa+T8G3IP7TGeO0tlWvj7rtVOAIgKM+qHp/fpIdPdMYPHqK401N8VHtW+Q/+v1+Qy+gFHfg=");
			request.setAlgo("SM2");
			request.setExtDn("C=CN,CN=测试用户");
			request.setExtInfo(EncodeUtil.base64Encode(CipherUtil.hash("SHA1", "证据hash".getBytes("UTF-8"))));
			request.setAppId(appId);
			request.setIdCard("120104197512116358");
			request.setIdCardType("SF");
			request.setName("测试用户");
			request.setMobile("13901234567");
			CertBean cert = signClient.requestOneCert(request);

			System.out.println("cert:" + cert.getCert());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * P10申请长效证书
	 * 
	 * @Description:
	 * @throws Exception
	 */
	@Test
	public void requestCertWithTime() throws Exception {
		OneCertRequest request = new OneCertRequest();
		request.setName("张三");
		request.setIdCard("110101199003072113");
		request.setIdCardType("SF");
		request.setMobile("13111111111");
		request.setAlgo("RSA");
		request.setAppId(appId);
		request.setExtDn("c=cn,cn=张三");
		request.setExtInfo("");
		request.setP10(
				"MIIClTCCAX0CAQAwUjELMAkGA1UEBhMCQ04xDTALBgNVBAoMBEJKQ0ExDTALBgNVBAsMBEZKTlgxJTAjBgNVBAMMHHpodXlzQDEwMUAzNjA3MzAxOTg4MDUyNzU0MzcwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCqF9dYmRM4cWIMHVLjPPnyZhYhxQ5kmdvBn+tAS+0nL45WnrxaMnjvZ9dquxTRTg1svo9JzoYfI/CD43nriWKk64AhcU+uUalkeyV0XyaRePs4u56q0koKy4stRSZF2kfNxsM0FJElBUXEtpnGyw5+8wR4zC+Oakwlsy2U59vZs9e1o36m9mtvz+/usdndmRzuUvuqRhq+bPQ1tACK7aZ74637rPY471mk37Tr/yGQZXwgH6dIy/6okWUn+gkpWD5bMlyhT3aUqU0fTVcrS6dM5c4wKuhwNdnGcQx3X+fndOm7QI4NQ80eAfw8rvu27II5Si/RnfowJGG/Dbv/yExTAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAETr+bx/6Iu0QQVzuikhgNa/Z3UnfywB1Qcbx4ygfYH0Xv4TNnjsBFup9hPIs3UL8HXwdy2OiX/flT0hVNIHXkRbV0qlVAA5faL3GfDqWqKJQNft1yPnQPPr4DwlJeFSq0INokgfCuQrUYs0O2dyVKd77HHEi6vjzPWc4AiuyOuqqPhFFVbzDD39NCNKJOWoQ+dYtA/IUmWrysPI6Tdbp98yV2MLbHdKHSnFgfgMNp/WRry0/WyAP0BnNsfqupMqemUAedfK1HMaoO5THXntB26bjHYPknuhnZE9XGDftZguBezNTX8awfXLLfgLKwvGYEVM3/X4emwOB72nF2KdptM=");
		request.setExpDate(1);
		CloudRespMessage resMessage = signClient.requestCertWithTime(request);
		System.out.println("状态码：" + resMessage.getStatusCode());
		System.out.println("状态信息：" + resMessage.getStatusInfo());
		if ("200".equals(resMessage.getStatusCode())) {
			System.out.println("cert:" + resMessage.getUserInfo().getUserCert());
		}
	}

	/**
	 * 申请企业托管证书
	 * 
	 * @Description:
	 * @throws Exception
	 */
	@Test
	public void requestEnterpriseDepositCert() throws Exception {
		EnterpriseCertRequest request = new EnterpriseCertRequest();
		request.setAppId(appId);
		request.setChannelId(channelId);
		request.setEnterpriseName("测试企业1");
		request.setOrganizationCode("444444");
		request.setUniqueNum("");
		request.setIcRegistrationNum("");
		request.setTexationNum("");
		request.setCertDn("CN=测试企业1,O=测试企业1,C=CN");
		request.setAlgo("RSA");
		request.setKeyLen(1024);
		CloudRespMessage resMessage = signClient.requestEnterpriseDepositCert(request);
		System.out.println("状态码：" + resMessage.getStatusCode());
		System.out.println("状态信息：" + resMessage.getStatusInfo());
		if ("200".equals(resMessage.getStatusCode())) {
			System.out.println("企业编号:" + resMessage.getUserInfo().getEnterpriseKeyID());
			System.out.println("企业证书:" + resMessage.getUserInfo().getUserCert());
		}
	}

	/**
	 * 注销企业托管密钥
	 * 
	 * @Description:
	 * @throws Exception
	 */
	@Test
	public void deleteEnterpriseDepositCert() throws Exception {
		ReqMessage reqMessage = new ReqMessage();
		reqMessage.setAppId(appId);
		UserInfoMessage userInfo = new UserInfoMessage();
		userInfo.setEnterpriseKeyID("ENT_110a2170-2829-4aaf-a031-00be6f39b35f");
		reqMessage.setUserinfo(userInfo);
		CloudRespMessage resMessage = signClient.deleteEnterpriseDepositCert(reqMessage);
		System.out.println("状态码：" + resMessage.getStatusCode());
		System.out.println("状态信息：" + resMessage.getStatusInfo());
		if ("200".equals(resMessage.getStatusCode())) {
			System.out.println("注销成功");
		}
	}

	/**
	 * 新添人脸信息
	 * 
	 * @throws Exception
	 */
	@Test
	public void addFaceData4UserTest1() throws Exception {
		String imgFile = "E:\\1.JPG";// 待处理的图片
		// String imgFile = "E:\\2.png";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String faceData = EncodeUtil.base64Encode(data);
		String msspIdcopy = "cf3b2f5f06086b2616ffb599a1c28a783ff2ba3e1f3cc18c984a090ec16c51e3";
		MSSGResponse mssgRes = signClient.addFaceData4User(msspIdcopy, faceData);
		System.out.println(mssgRes.getRetCode());
		System.out.println(mssgRes.getMessage());
		Assert.assertEquals(mssgRes.getRetCode(), 0);

	}

	/**
	 * 人脸对比
	 * 
	 * @throws Exception
	 */
	@Test
	public void compareUserFacePicture() throws Exception {
		String imgFile = "E:\\1.JPG";// 待处理的图片
		// String imgFile = "E:\\2.png";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String faceData = EncodeUtil.base64Encode(data);
		String msspIdcopy = "cf3b2f5f06086b2616ffb599a1c28a783ff2ba3e1f3cc18c984a090ec16c51e3";
		MSSGResponse mssgRes = signClient.compareUserFacePicture(
				"321176e98a1832af3c7bcad9f2039c50e7a0b975fb41ba051613b01646a09e62",
				"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");
		System.out.println(mssgRes.getRetCode());
		System.out.println(mssgRes.getMessage());
		Assert.assertEquals(mssgRes.getRetCode(), 0);
	}

	/**
	 * 更新个人用户激活码
	 */
	@Test
	public void updateMsspUserAuthCodeTest() {

		try {
			CloudRespMessage resMessage = signClient.updateMsspUserAuthCode(msspId);
			System.out.println(resMessage.getStatusCode());
			System.out.println(resMessage.getStatusInfo());
			if (resMessage != null && "200".equals(resMessage.getStatusCode())) {
				System.out.println("添加用户结果: keyid=" + resMessage.getUserInfo().getKeyID() + " authcode="
						+ resMessage.getUserInfo().getAuthCode() + "  qrcode="
						+ resMessage.getUserInfo().getUserQrCode());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 异步签名 批量 添加签名任务，增加有效期字段
	 * 
	 * @throws Exception
	 */
	@Test
	public void batchSignData4User() throws Exception {
		try {
			List<ClientSignBean> signDataList = new ArrayList<ClientSignBean>();
			// signDataList.add(ClientSignBean.createClientSignBean("uniqueid111111",
			// EncodeUtil.base64Encode("data123".getBytes("UTF-8")), null, null));
			// signDataList.add(ClientSignBean.createClientSignBean("uniqueid222222",
			// EncodeUtil.base64Encode("data456".getBytes("UTF-8")), null, null));
			signDataList.add(ClientSignBean.createClientSignBean("uniqueid111111",
					"vzTQY/7bRhp6ShQwwY3o7exlPFL7RDikYJKUv/n73ko=", null, null));
			// signDataList.add(ClientSignBean.createClientSignBean("uniqueid222222",
			// "qsnNkvBNIzk8jYRfqmTOpqzeJS+zrIVxfrTkx6/ei0c=", null, null));
			// String expiryDate = System.currentTimeMillis() +
			// 10000+"";//有效期，单位ms，默认三天259200000
			MSSPSignGroupResult res = signClient.batchSignData4User(CommonClientConstant.DATA_TYPE_HASH,
					algo_SM3withSM2, "description", msspId, null, signDataList);
			System.out.println("批量编号:" + res.getSignGroupId());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 异步签名 批量 获取结果
	 * 
	 * @throws Exception
	 */
	@Test
	public void getBatchSignData() throws Exception {
		try {
			String signGroupId = "SDG_c7e8e5dd-4ad8-4bea-924c-b0cc3ce4d4e3";
			MSSPSignGroupResult res = signClient.getBatchSignData(signGroupId);
			if (res.getSignResultList() != null) {
				for (MSSPSignResult beanObj : res.getSignResultList()) {
					System.out.println(beanObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 批量签名查询签名状态
	 */
	@Test
	public void getBatchSignStatus() throws Exception {
		try {
			String signGroupId = "SDG_c7e8e5dd-4ad8-4bea-924c-b0cc3ce4d4e3";
			String res = signClient.getBatchSignStatus(signGroupId);
			System.out.println("GetBatchSignStatus  批量状态==" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 协同认证签名添加任务
	 * 
	 * @throws Exception
	 */
	@Test
	public void signAuth4User() throws Exception {
		try {
			String res = signClient.signAuth4User(plain_data, algo_SHA1withRSA, "Log in Server1");
			System.out.println("signAuth4User result==" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 协同认证签名获取签名结果
	 * 
	 * @throws Exception
	 */
	@Test
	public void getSignData() throws Exception {
		try {
			MSSPSignResult res = signClient.getSignData("SD_a973355e-8b8a-4b9a-8ffc-3374b439d977");
			System.out.println("signResult==" + res.getSignResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量托管秘钥数据签名，支持byte、hash
	 * 
	 * @throws Exception
	 */
	@Test
	public void batchAuthorizeSignData4User() throws Exception {
		try {
			List<ClientSignBean> signDataList = new ArrayList<ClientSignBean>();
			signDataList.add(ClientSignBean.createClientSignBean("uniqueid111111",
					"W2B+Xc340rMxMjNbYH5dUjBsR09EbGhlQUE4QVBjQUFQNEZCZjRHQnY0WUdQNDdPLzVRVVA2VmxmN1cxdjdyNi83Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92Nysvdi8vLy83Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92Ly8vLzcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3Y3Ky92NysvdjcrL3YvLy8vNysvdjcrL3Y3Ky92Ly8veUg1QkFsa0FQQUFMQUFBQUFCNEFEd0FBQWorQU9FSkhFaXdvTUdEQ0JNcVhNaXdvY09IRUNOS25FaXhvc1dMR0ROcTNNaXhvOGVQSUVPS0hFbXlwTW1US0ZPcVhNbXlwY3VYTUdQS25FbXpwczJiT0hQcTNNbXpwOCtmUUlNS0hVcTBKNENpTW84YUJLQTBaTk9SVEo5MmxDcVFxY2VvV0oxR3JVcFY0OUd0VloyV1ZHcTFxMFdzVmdtbS9iaFdLMW16R2FuQ2pUdlhLOWU2RjhHMkJZa1dMTWU5VjgwQ3J0Z1g3OGErYlBjT25paFhKRnExVTlNK1hXeVhiMVBEWjdrT0pDdVdKR2Q0aittdXZZeFpZbW1LaFU4N3ZIeFFOVVRYREIrejVndTZ0ZU94bEwzNnJYMGJkMjdHdXplN2hXMTZNL0dJdnpFbVZ5NTFPZUhqUDZFam5VNjl1dlhyMkxOcjM4Njl1L2Z2NE1NVWl4OVB2cno1OCtqVHExL1B2cjM3OS9BUEJnUUFPdz09W2B+XQ==", null, null));
			// signDataList.add(ClientSignBean.createClientSignBean("uniqueid222222",
			// EncodeUtil.base64Encode("data456".getBytes("UTF-8")), null, null));

			MSSPSignGroupResult res = signClient.batchAuthorizeSignData4User(CommonClientConstant.DATA_TYPE_BYTE,
					algo_SHA1withRSA, "", msspId, signDataList);
			if (res.getSignResultList() != null) {
				for (MSSPSignResult beanObj : res.getSignResultList()) {
					System.out.println(beanObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 异步签名 托管秘钥 添加签名任务 支持授权信息如pin码、短信验证码 dataType:PLAIN、BYTE、HASH
	 * 
	 * @throws Exception
	 */
	@Test
	public void signDataGroupWithAuthAddJob() throws Exception {
		try {
			List<ClientSignBean> signDataList = new ArrayList<ClientSignBean>();
			signDataList.add(ClientSignBean.createClientSignBean("uniqueid111111",
					EncodeUtil.base64Encode("data123".getBytes("UTF-8")), null, null));
			// signDataList.add(ClientSignBean.createClientSignBean("uniqueid222222",
			// EncodeUtil.base64Encode("data456".getBytes("UTF-8")), null, null));
			MSSPSignGroupResult res = signClient.signDataGroupWithAuthAddJob(CommonClientConstant.DATA_TYPE_PLAIN,
					algo_SHA1withRSA, "description", "d6ab7e3c34c293683a5b19a636a65dba2e3084e224758ae3c4b78c34da6add15",
					signDataList);
			System.out.println("SignDataGroupAddJob  批量编号==" + res.getSignGroupId());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 异步签名 托管秘钥 获取结果 支持授权信息如pin码、短信验证码
	 * 
	 * @throws Exception
	 */
	@Test
	public void signDataGroupWithAuthGetResult() throws Exception {
		try {
			ReqMessage reqMessage = new ReqMessage();
			reqMessage.setSignGroupId("SDG_e7e16414-e0a5-498a-b36b-c1c46d8d14cc");
			SignAuthInfo signAuthInfo = new SignAuthInfo();// 授权信息
			signAuthInfo.setChallengeOpt("");// 短信校验码
			signAuthInfo.setEncPin("888888");// PIN码
			signAuthInfo.setChallengeSign("");// 挑战签名任务ID
			signAuthInfo.setFaceData("");// 人脸
			reqMessage.setSignAuthInfo(signAuthInfo);
			MSSPSignGroupResult res = signClient.signDataGroupWithAuthGetResult(reqMessage);
			System.out.println("SignDataGroupGetResult  批量编号==" + res.getSignGroupId());
			if (res.getSignResultList() != null) {
				for (MSSPSignResult beanObj : res.getSignResultList()) {
					System.out.println(beanObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取托管签章企业令牌
	 * 
	 * @throws Exception
	 */
	@Test
	public void authSign4Enterprise() throws Exception {
		try {
			UserInfoMessage userInfo = new UserInfoMessage();
			userInfo.addCreditCode("ORG", "111111111111111111");
			userInfo.setChannelId(channelId);
			SignRequest signDataRequest = new SignRequest();
			signDataRequest.setData("3260e92f709f4aa4b40e0a0f9b330ef0");
			signDataRequest.setDataType("PLAIN");
			signDataRequest.setSignAlgo("SHA1withRSA");

			CloudRespMessage resMessage = signClient.authSign4Enterprise(appId, "2018042700006", userInfo,
					signDataRequest);
			if ("200".equals(resMessage.getStatusCode())) {
				System.out.println("证书:" + resMessage.getSignResult().getSignCert());
				System.out.println("签名值:" + resMessage.getSignResult().getSignResult());
				System.out.println("断言:" + resMessage.getSignResult().getSignData());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 企业托管密钥签名
	 * 
	 * @Description:
	 * @throws Exception
	 */
	@Test
	public void enterpriseDepositSign() throws Exception {
		ReqMessage reqMessage = new ReqMessage();
		reqMessage.setAppId(appId);
		UserInfoMessage userInfo = new UserInfoMessage();
		userInfo.setEnterpriseKeyID("ENT_ad41c253-a27b-4226-9862-25088285010a");
		reqMessage.setUserinfo(userInfo);
		SignRequest signRequest = new SignRequest();
		// 签名数据 string原文需要转byte之后再base64转String ； byte原文需要base64转string；
		// hash原文需要base64转string
		signRequest.setData(EncodeUtil.base64Encode("原文".getBytes("UTF-8")));
		// signRequest.setData("wee");
		// 数据类型 PLAIN-string数据 ；BYTE-byte数据； HASH-hash数据
		signRequest.setDataType("PLAIN");
		signRequest.setSignAlgo("SHA1withRSA");
		reqMessage.setSignDataRequest(signRequest);
		CloudRespMessage resMessage = signClient.enterpriseDepositSign(reqMessage);
		System.out.println("状态码：" + resMessage.getStatusCode());
		System.out.println("状态信息：" + resMessage.getStatusInfo());
		if ("200".equals(resMessage.getStatusCode())) {
			System.out.println("签名值:" + resMessage.getSignResult().getSignResult());
		}
	}

	
	/**
	 * 网页个人托管网页签章
	 * 
	 * @throws Exception
	 */
	@Test
	public void authorizePersonEssSign() throws Exception {
		try {
			String res = signClient.authorizePersonEssSign(plain_data.getBytes("GBK"), algo_SHA1withRSA, "description", msspId);
			System.out.println("sign4ESS result==" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 网页签章添加任务
	 * 
	 * @throws Exception
	 */
	@Test
	public void signData4ESS() throws Exception {
		try {
			String res = signClient.signData4ESS(plain_data.getBytes("UTF-8"), algo_SHA1withRSA, "description", msspId);
			System.out.println("sign4ESS result==" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 网页签章获取结果
	 * 
	 * @throws Exception
	 */
	@Test
	public void getSignSealData4ESS() throws Exception {
		try {
			String res = signClient.getSignSealData4ESS("SWP_328b17e1-d5be-4af0-bdfc-c48641d0bcc2");
			System.out.println("getSignData result==" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * KEY网页签章生成原文
	 */
	@Test
	public void genSignEssHash() {
		String data = "网页123";
		String cert = "MIICIzCCAcmgAwIBAgIJECEDAAAAAByNMAoGCCqBHM9VAYN1MGwxCzAJBgNVBAYTAkNOMRAwDgYDVQQIDAdHdWFuZ3hpMRAwDgYDVQQHDAdOYW5uaW5nMQ0wCwYDVQQKDARHWENBMQ0wCwYDVQQLDARHWENBMRswGQYDVQQDDBJHdWFuZ3hpIFNNMiBNb2JpbGUwHhcNMTkwMTIxMDUwOTE3WhcNMjQwMTIxMDYwOTE3WjBxMQswCQYDVQQGDAJDTjEQMA4GA1UEAwwH5byg5LiJNDFQME4GCgmSJomT8ixkAQEMQGQ3ODVkNDEyNTBjNmMwMjExMTk4NTNkODBmYmQzZDljMTRkMGE0ZThiNzIxZmQ2MTc1NWUzOTFkYzJlOTEzZjcwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAASTdhPCEHVWNmGLaxM3oyrx84f+ESq1cojBSKixFzoMiPuGf1xpgj8/6Qtl5YVIs2DfNBobV4epMBKjdRdb7WKNo08wTTAfBgNVHSMEGDAWgBTDPvpc+x6W2N+lIc7S6QsEK9b0ZzAdBgNVHQ4EFgQUv8QFeTAEkUkU+V24OfeN9K5YrzkwCwYDVR0PBAQDAgeAMAoGCCqBHM9VAYN1A0gAMEUCIEsgfyhZbUGAeR/HujPknVMwncH7NUWs4ItPI1lWgBw/AiEA3kJCfE7ApEEmJbHQHK6d5Mjrm/odZt3Gi/YCGvkcCNo=";
		String stamp  = "R0lGODlheAA8APcAAP4FBf4GBv4YGP47O/5QUP6Vlf7W1v7r6/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v////7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v////7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v////7+/v7+/v7+/v///yH5BAlkAPAALAAAAAB4ADwAAAj+AOEJHEiwoMGDCBMqXMiwocOHECNKnEixosWLGDNq3Mixo8ePIEOKHEmypMmTKFOqXMmypcuXMGPKnEmzps2bOHPq3Mmzp8+fQIMKHUq0J4CiMo8aBKA0ZNORTJ92lCqQqceoWJ1GrUpV49GtVZ2WVGq1q0WsVgmm/bhWK1mzGanCjTvXK9e6F8G2BYkWLMe9V80CrtgX78a+bPcOnihXJFq1U9M+XWyXb1PDZ7kOJCuWJGd4j+muvYxZYmmKhU87vHxQNUTXDB+z5gu6teOxlL36rX0bd27Guze7hW16M/GIvzEmVy51OeHjP6EjnU69uvXr2LNr3869u/fv4MMUix9Pvrz58+jTq1/Pvr379/APBgQAOw==";
		String charsetName = "GBK";
		String digestAlg = "SM3";
		try {
			String res = signClient.genSignEssHash(data, stamp, cert, charsetName,digestAlg);
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * KEY网页签章生成签名值
	 */
	@Test
	public void authorizeSignEss() {

		String cert = "MIIDMzCCAhugAwIBAgIJIBA1AAAAIcENMA0GCSqGSIb3DQEBBQUAMDExCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMRMwEQYDVQQDDAptc3NwdGVzdGNhMB4XDTE4MDgwMjA1MzgyNFoXDTIzMDgwMjA2MzgyNFowcDELMAkGA1UEBgwCQ04xDzANBgNVBAMMBuW8oOWbmzFQME4GCgmSJomT8ixkAQEMQGM3NThlYmVjNzdmNjYxOWM3ZGY5ZWY1Zjg3MGQ5ZmI3NzZhYTQ5ZjBiMzg5NGFjZjkyNGJkMTVjYTNkYjJlYTAwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBALG0B+B1HhQ5lZ88K0vyqKDoUuOOca4LOxo6uZfyrLOgKwgXpu13uQCXGLeYKUbXulxKmxCvTmb5XFjKnDzQQVIvpFraLGRP+1u2y9N03QodBDeQRznj4cxQWe1RsHgk7pzTO+BJ6Lmm9IGCFCtpbWNLYhLAVfmDjYkr+fJY/CRzAgMBAAGjgZIwgY8wHwYDVR0jBBgwFoAUfgaZPuO42ZCfGehz3Oc2ZlUYtuMwHQYDVR0OBBYEFOE7ZNuy8HVlSHLrKWy8QgiP+f9SMAsGA1UdDwQEAwIGwDBABgNVHSAEOTA3MDUGCSqBHIbvMgICAzAoMCYGCCsGAQUFBwIBFhpodHRwOi8vd3d3LmJqY2Eub3JnLmNuL2NwczANBgkqhkiG9w0BAQUFAAOCAQEAWdb9iwDSgruzfPJmw/S5G35cHgGivkXMvVP/IdgoDWowpKT9Q6Ti/MQdZC0uvy74SQkww9/ObAFVqY3ez9cW322cr4VnwaORMmNAefm/vZmTgDCCGZW4lc6WEb6HzE267SisTIF68tAqabL+U4va+JuOfjJanNYirouTjABSbFEQXKYTFv6QVUi4GZZnLkOofy9hBMH2KjbC4vxxWnLcid/v6MEVODyn09W9Uhcoty+2UtKV4K4gCC2emgan+CnQktkBaVj+W6P/OtR2kM7McY94f1bHYWe+cxRgWIIeDmtuR4sP4auLHho8qWoObVC2HVDx+KbGdlYhQ96Q9ewJBw==";
		String stamp = "R0lGODlheAA8APcAAP4FBf4GBv4YGP47O/5QUP6Vlf7W1v7r6/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v////7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v////7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v////7+/v7+/v7+/v///yH5BAlkAPAALAAAAAB4ADwAAAj+AOEJHEiwoMGDCBMqXMiwocOHECNKnEixosWLGDNq3Mixo8ePIEOKHEmypMmTKFOqXMmypcuXMGPKnEmzps2bOHPq3Mmzp8+fQIMKHUq0J4CiMo8aBKA0ZNORTJ92lCqQqceoWJ1GrUpV49GtVZ2WVGq1q0WsVgmm/bhWK1mzGanCjTvXK9e6F8G2BYkWLMe9V80CrtgX78a+bPcOnihXJFq1U9M+XWyXb1PDZ7kOJCuWJGd4j+muvYxZYmmKhU87vHxQNUTXDB+z5gu6teOxlL36rX0bd27Guze7hW16M/GIvzEmVy51OeHjP6EjnU69uvXr2LNr3869u/fv4MMUix9Pvrz58+jTq1/Pvr379/APBgQAOw==";
		String signValue = "Mx+NuEJwXKyjcoZo0r6C5e1WPZhVr/UbcpSnv/BFfXmu4NWo5wp9ObxuW1n3iWUzS5lbMlUaxZwJV93RSaKnrPVgBSMdtZqpdRB8s3dWZH37REMAZt+I5Dr23/QW1Usu34Y3Mhc0T8wSe0JALRBxxY+WDqWV/leELkiX4w28cV0=";
		try {
			String res = signClient.authorizeSignEss(signValue, cert, stamp);
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 网页签章签名值验签
	 * 
	 * @throws Exception
	 */
	@Test
	public void verifySign4ESS() throws Exception {
		try {
			String signRes = "L2zDRtuSheJauPxuOfGvLKdEMkK4o0C2cKDbqGiTA2IQxxDjwX4s+/QsV8O/I5OMaP8hfpMebBv+tFE5hcfLI4o/iIAtqHOt7z19Q3ie66FHUlGUaWMeo41GpbT+I/yIcLrFJUY7LxKPunkU43N2+h4By/6cy7uQ572m0kdzA48=````MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDVEURLyEpjiXgxMSxxo7QynelD7agkNnKyC1ZccGK/bnuLinBfd5T9wUcMPKn1HMFafqzgYViFHnU5Vl/8A+7lqiezStVzMOSRKqSvlxYyeG6bADimG8d6wElXpmr8qk3GsexB+Eycwnb711dkJkfduTeZ9dMfMrpmVCw4xoQ7nQIDAQAB````MIICvjCCAaagAwIBAgIIGmlVbHm504UwDQYJKoZIhvcNAQELBQAwQTELMAkGA1UEBhMCY24xDTALBgNVBAoMBGJqY2ExFDASBgNVBAsMC3Rlc3RhbmRyb2lkMQ0wCwYDVQQDDARteWNhMB4XDTE4MDgwODA1MDIwN1oXDTE5MDgwODA2MDIwN1owcDFQME4GCgmSJomT8ixkAQEMQDIwMmUxOTZkNTM4MDAwZGEzMmMwM2U3MDdjNTdjOTFjN2YwZDI5OGFmYTY4ZjE2NWY1ZDU0YmI1ZWIwYTNhNDQxDzANBgNVBAMMBuW8oOWbmzELMAkGA1UEBhMCQ04wgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBANURREvISmOJeDExLHGjtDKd6UPtqCQ2crILVlxwYr9ue4uKcF93lP3BRww8qfUcwVp+rOBhWIUedTlWX/wD7uWqJ7NK1XMw5JEqpK+XFjJ4bpsAOKYbx3rASVemavyqTcax7EH4TJzCdvvXV2QmR925N5n10x8yumZULDjGhDudAgMBAAGjDzANMAsGA1UdDwQEAwIGwDANBgkqhkiG9w0BAQsFAAOCAQEAddmNpSZw2BvdiAFF9Iq7EicL9ANCFj6KLQUJP2Ph+BSzj8GFTqfzQewYyZ61yR/jIqA8tzjq9f81LSyaY0ALG8lfMJkWF3cuOK0kMPIDRtZG077QOFa1uDoyRDXkj6d9cQCRJvyJiwTRRIx/7yUHvz9KK3EwZwn3wj+DG+jJcBc+wdZXuAD2TbM+tBjLUNhd0yCAIWBXffOfcvDBwEqPjCOLMWMv5qzoEjuJUj68JqHXGuEAgAhyIhMbnuBqoNuAN4yN5vZ/S3JmYJNZfdOddKq940Rn4wRnoWzb7oy6VVZgzz0t4s8y/dk2YkC8G5iL+dgng5Kc9fNC+mflUSDTpA==````1533784036````````[`~][`~]R0lGODlhUAA8APcAAP4AAP4AAP4AAP4AAP4AAP4AAP4AAP4AAP4AAP4AAP0AAPwAAPsAAPkAAPcAAPQAAPABAesBAeQCAtoDA80EBLwGBqUICIkLC20PD04TEzYWFigZGRwcHB0dHR4eHh8fHyAgICEhISIiIiMjIyQkJCUlJSYmJicnJygoKCkpKSoqKisrKywsLC0tLS4uLi8vLz09PZ6enuTk5Pv7+/7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v////7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v7+/v////7+/v7+/v7+/v///yH5BAlkAPAALAAAAABQADwAAAjzAOEJHEiwoMGDCBMqXMiwocOHECNKnEixosWLGDNq3Mixo8ePIEOKHEmypMmTKFOqXMmypcuXMGPKnEmzps2bOB0iAImg5054PQX6/HlxaFCgPAcePYqUI1GiQjU+/ckUasanBJkWVaoUq1OuUTd6BTrWolGwVjGOrXoVLNK0at2yjWtQq1i5ZStCPXuWblOybs0O5Qp3a1jAh83KTdz2L2LHjZsW1vtY6OCvhydP9KlQs8S9gdV69hwR9Nu7C0lDBD31qtHXdvVqtauaIenaDa3iLm07p+/fwIMLH068uPHjyJMrX868ufPn0KNLn069uu+AADs=[`~]";
			boolean res = signClient.verifySign4ESS(plain_data.getBytes("UTF-8"), signRes, dialgo_SHA1);
			System.out.println("verifySign result==" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 托管秘钥使用授权信息网页签章 添加任务
	 * 
	 * @throws Exception
	 */
	@Test
	public void authorizeSignWithAuth4ESSAddJob() throws Exception {
		try {
			String signId = signClient.authorizeSignWithAuth4ESSAddJob(plain_data.getBytes("UTF-8"), algo_SM3withSM2,
					"description", msspId);
			System.out.println("signId:" + signId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 时间戳同步签名
	 * 
	 * @throws Exception
	 */
	@Test
	public void signTss() throws Exception {
		try {
			String res = signClient.signTSS(EncodeUtil.base64Encode("sign tss data".getBytes("UTF-8")),
					algo_SHA1withRSA);
			System.out.println("SignTss result==" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 时间戳验签
	 * 
	 * @throws Exception
	 */
	@Test
	public void verifyTss() throws Exception {
		// String
		// signRes="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";
		String signRes = "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";
		VerifyTSSClientDataInfo res = null;
		MSSPSignResult request = new MSSPSignResult();
		request.setSignAlg(algo_SHA1withRSA);
		request.setSignCert(null);
		// request.setSignData(EncodeUtil.base64Encode("sign tss
		// data".getBytes("UTF-8")));
		request.setSignData(
				"HlPAZqugjX8qJzSwXnaa72fTQ+a9kLNYP3ek0yAbzmf5hMZEgALvvElqVs7jKI2XgExcIkjihA27XjAtot/lqPQxzBS6JVQVskXtGj89BzyXIaNqUG6yTgceuHj0Oprq7vAhuKxr1wJrPJByhMRJpmSslN+wDiRofgVbN59iuROABSpWGCte6wuQ+MTd1ZJU4V3CSVbMf/uYBUov70OBhjRLMwtSTRerD0OJqsnwNlib+Z4Ti/qFCD24eed3lZHPcBpsgSptbBbLiqoBNo2u96FllrEX3/rq/U15LAHR6+55xGkaWnmpnZQw3fQ/G4GMoY55xbCUJc3FvNWUPyxS0w==");
		request.setSignResult(signRes);

		res = signClient.verifyTss(request);
		System.out.println("verifyTss result==" + res.getResult() + " time=" + res.getTime());

	}

	/**
	 * 信手书事件证书签名
	 * 
	 * @throws Exception
	 */
	@Test
	public void oneCertSignData() throws Exception {
		try {
			OneCertSignDataRequest request = new OneCertSignDataRequest();
			request.setAppId(appId);
			// request.setEncPackage("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");//加密包
			// request.setEncPackage("{\"Digest\":{\"Alg\":\"CRC32\",\"Value\":\"1954D714\"},\"EncCertSN\":\"1b40000000000005db00\",\"EncData\":\"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\",\"EncKey\":\"LDDULlq1rMvhpUZuigWPVXVgWWwYmykbwWiNkYRfZXp+Rw075Rrmjk374SLFHWu0vs8EKRScVoUVuIXe/YSASlv2CDG23cWUPU1fs7KmCqPyq5z33l1rJzCyPGVrr6Cq1/dsuQ51c1XPgFVrC7Gx6xlqqiFNAIIqgchUOL/WXdY=\",\"PROModel\":\"MSDS\",\"Version\":\"1.6\"}");//加密包
			request.setEncPackage(
					"{\"Version\":\"4.7\",\"EncCertSN\":\"1b40000000000005db00\",\"EncKey\":\"ZaUvLPOFMSJIYNYh0Nc6ttv9rM2J3TWKBYdocGmaR7Zg2+5uAnV/iknV4brqkGWp4F2Wu7yNorBlIuRfhXKjKgo9wWG0V8P7ioaidZJWEMCGZUdC9mdxogunwE30xNuK44+ByyIwmBFvEWEgqQbIyTyTQhmwQrnrvBLJPKSoc2Y=\",\"PROModel\":\"WSDS\",\"Digest\":{\"Alg\":\"CRC32\",\"Value\":\"360D4C9D\"},\"EncData\":\"DBYlv5AnUbw0UDnMGWBtdL4bHL16BAeayiX1twlWGqVN2K4sAeQ4OYwQvbVtdg0/i6YZOBkQPLN2UhpDuXxbudgnyi0xgmln8LajiAyJZVgtUItXw8f5zJiOjez5aCl99zPdViW2gN79FqeTcOkBHV7rdeESJxZldJsEXwE9wYx84TGADHgucEnULDdj8D18JvCgs2gGzBJ+HpeEM2q5GZif+96PhSea8S1DiYXvRX3OdgCKwZFt2y6tb6kNct5SN92U4uCMmjJ0UTt8ltHCotzkw2AhPjPoi6PuxcOH+UJOOet8dlIAUrf1CUfBrq5ntRAoNu4AHzSFS3xiZJarKBvGJK0mhEZfd9/3T41hR+/Te5rXlyTezrR8dsZsIIoP\"}");// 加密包
			OneCertSignDataResponse response = signClient.oneCertSignData(request);
			System.out.println("retCode:" + response.getRetCode());
			System.out.println("message:" + response.getMessage());
			System.out.println("cert:" + response.getCert());
			System.out.println("signature:" + response.getSignature());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取认证签名随机数
	 * 
	 * @throws Exception
	 */
	@Test
	public void initAuth() throws Exception {
		try {
			ReqMessage reqMessage = new ReqMessage();
			reqMessage.setAppId(appId);

			CloudRespMessage resMessage = signClient.initAuth(reqMessage);
			System.out.println("状态码：" + resMessage.getStatusCode());
			System.out.println("状态信息：" + resMessage.getStatusInfo());
			if (resMessage != null && "200".equals(resMessage.getStatusCode())) {
				System.out.println("随机数:" + resMessage.getNonce());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 证书注册企业
	 * 
	 * @throws Exception
	 */
	@Test
	public void addEnterpriseByKey() throws Exception {

		try {
			String signCert = "MIIBqDCCAUygAwIBAgIIZkDNNVdSI+gwDAYIKoEcz1UBg3UFADAvMREwDwYDVQQDDAhiamNhdGVzdDENMAsGA1UECgwEYmpjYTELMAkGA1UEBhMCY24wHhcNMTgwODA4MDUwMjA3WhcNMTkwODA4MDYwMjA3WjBwMVAwTgYKCZImiZPyLGQBAQxAMjAyZTE5NmQ1MzgwMDBkYTMyYzAzZTcwN2M1N2M5MWM3ZjBkMjk4YWZhNjhmMTY1ZjVkNTRiYjVlYjBhM2E0NDEPMA0GA1UEAwwG5byg5ZubMQswCQYDVQQGEwJDTjBZMBMGByqGSM49AgEGCCqBHM9VAYItA0IABASh0tfhER8I1eDfzYxha0NgLrLS9xDCAW88xtAIqtQCzFLaCyMV3StZWAiCrs2mqxc2tegcSESs62NHeE8yrrqjDzANMAsGA1UdDwQEAwIGwDAMBggqgRzPVQGDdQUAA0gAMEUCIQDd24k8vK8XB3O8vNNov3nTqMmilgXxsTSWM8uJvQ0AsQIgUVbmtKT0OwUFSI0EMoRwjvbMja0svJb1SAtVxCxxVps=";
			String signData = "Q+3+9VSlE2r0ZFmX8ZvoHFw5EFnKM6epCeZAUwqr55l4YeSOcz8qBrFgCAppaVxMYtD+X9UrYSNjS7imciuJTQd6dWkmmxlOJPTY050yQ2H63H0CEGSS232bMvZbcLCEav3dQzo4R5P9ztTFNTqWVfQ58U1JZd3/aK5Dm4dBjf0=vzTQY/7bRhp6ShQwwY3o7exlPFL7RDikYJKUv/n73ko=";
			ReqMessage reqMessage = new ReqMessage();

			reqMessage.setAppId("APP_6D71908FAACBB114");
			UserInfoMessage user = new UserInfoMessage();
			user.setUserName("uername11"); // 企业用户姓名
			user.setEmail("abc@123.com");
			user.setMobilePhone("13901234567");
			user.setChannelId("CHN_2F894C2F3082E206");

			List<String> templateNums = new ArrayList<String>();
			templateNums.add("08893b6527bf43fe9b4322d30a89cb62");
			templateNums.add("TMP_a75a166b9df345f895dfe73993b38a5b");
			user.setTemplateNumList(templateNums);

			reqMessage.setUserinfo(user);
			reqMessage.setSignCert(signCert);
			reqMessage.setSignData(signData);
			reqMessage.setNonce(EncodeUtil.base64Encode("91998c4e3c1c4348861b72361338afbe".getBytes("UTF-8")));

			CloudRespMessage resMessage = signClient.addEnterpriseByKey(reqMessage);
			System.out.println("   状态码：" + resMessage.getStatusCode());
			System.out.println("状态信息：" + resMessage.getStatusInfo());
			if (resMessage != null && "200".equals(resMessage.getStatusCode())) {
				System.out.println("企业编码:" + resMessage.getUserInfo().getEnterpriseKeyID());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据企业签章任务id查询签章结果，包括签章图片、签名值、签名数据
	 * 
	 * @throws Exception
	 */
	@Test
	public void getEnterpriseSignSealData() throws Exception {
		try {
			String signId = "SDG_33f80544-574b-42f9-a1ca-61688c46b3a2";
			String ruleNum = "32ECA8B48DA69667";
			SignSealResult ssr = signClient.getEnterpriseSignSealData(channelId, ruleNum, signId);
			System.out.println("signData:" + ssr.getSignResult().getSignData());
			System.out.println("signResult:" + ssr.getSignResult().getSignResult());
			System.out.println("sealName:" + ssr.getUserSeal().getSealName());
			System.out.println("sealImg:" + ssr.getUserSeal().getSealImg());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证签名
	 * 
	 * @throws Exception
	 */
	@Test
	public void verifySign() throws Exception {
		try {
			String signRes = "MEUCIQDHRv7Sxn2yvge45jdM980IN+EoxrkRdFNsSiVJlylTUQIgGUic2x9gZEc9tmPR1DkjuQQi7Thtz0XNiK/9sk8EU9k=";// 签名值
			String cert = "MIICLzCCAdSgAwIBAgIJIBA0AAAAJfbPMAoGCCqBHM9VAYN1MDExCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMRMwEQYDVQQDDAptc3NwdGVzdGNhMB4XDTE4MDUyOTA2MDQzMloXDTIzMDUyOTA3MDQzMlowczELMAkGA1UEBgwCQ04xEjAQBgNVBAMMCeadjuaZk+WQmzFQME4GCgmSJomT8ixkAQEMQDJmODE3NWNjYTBkNGM1ZTMyZjhmZmY1NDMyMTdiNjBiNWVhYjEzOTExMGE3NjVlZTQxNmEzNWIxOTI2NTIwYWIwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAAREzhHGkVGGmjA1la4YwPEGBp1gOszrFmmXP9woW15YfbxbkeuXvAZW1HTPydOE1Ykv11/pRV2slUpkNQNtIxPXo4GSMIGPMB8GA1UdIwQYMBaAFAPp1JzWlhwVTUzsiWBccmZ+cO+/MB0GA1UdDgQWBBSEkw+Z4q6sBwr5HoZOmrXDUqYASTALBgNVHQ8EBAMCB4AwQAYDVR0gBDkwNzA1BgkqgRyG7zICAgMwKDAmBggrBgEFBQcCARYaaHR0cDovL3d3dy5iamNhLm9yZy5jbi9jcHMwCgYIKoEcz1UBg3UDSQAwRgIhAKbIefIS9pX6uKnjf010iMCE/wWNpFBsTzDwlpR4Jk8IAiEA6Itl+Tk2XreHCvODYDWvGb6twid/LDVVqWsXwGcYsYo=";
			String plain = "MTA1RjhGOTkzQzRFRkQ0MkQ2MA==";
			Boolean res = signClient.verifySign(EncodeUtil.base64Decode(plain), EncodeUtil.base64Decode(signRes), cert,
					dialgo_SM3);
			System.out.println("verifySign result==" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 证书验证
	 * 
	 * @throws Exception
	 */
	@Test
	public void verifyCert() throws Exception {
		try {
			CertVerifyResult res = signClient.verifyCert(appId, cert_rsa);
			System.out.println("verifyCert result==" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// -----------------------------------后面两个文档没有---------------------------------------------------//
	/**
	 * 协同签名 获取签名结果、签名证书、签章图片
	 */
	@Test
	public void getSignSealData() throws Exception {
		try {
			SignSealResult res = signClient.getSignSealData("SD_0f013f2f-0996-475a-be0a-8c81fc643b7d");
			System.out.println("signResult==" + res.getSignResult().getSignResult());
			System.out.println("signCert==" + res.getSignResult().getSignCert());
			System.out.println("signAlg==" + res.getSignResult().getSignAlg());
			System.out.println("signData==" + res.getSignResult().getSignData());
			System.out.println("userSeal==" + res.getUserSeal().getSealImg());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 托管秘钥使用授权信息网页签章 获取结果
	 * 结果格式：签名值````公钥信息````证书信息````签名时间````容器名称````[`~]签章位置信息[`~]印章图片数据[`~]
	 * 
	 * @throws Exception
	 */
	@Test
	public void authorizeSignWithAuth4ESSGetResult() throws Exception {
		try {
			ReqMessage reqMessage = new ReqMessage();
			reqMessage.setSignGroupId("SWP_02bbd26b-2cd3-4b7a-bca9-d8115b0f7667");
			SignAuthInfo signAuthInfo = new SignAuthInfo();// 授权信息
			signAuthInfo.setChallengeOpt("");// 短信校验码
			signAuthInfo.setEncPin("888888");// PIN码
			signAuthInfo.setChallengeSign("");// 挑战签名任务ID
			reqMessage.setSignAuthInfo(signAuthInfo);
			MSSPSignGroupResult res = signClient.signDataGroupWithAuthGetResult(reqMessage);
			System.out.println("SignDataGroupGetResult  批量编号==" + res.getSignGroupId());
			if (res.getSignResultList() != null) {
				for (MSSPSignResult beanObj : res.getSignResultList()) {
					System.out.println("signResult:" + beanObj.getSignResult());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void aaa() throws Exception {
//		System.out.println(CertificateUtil.getCertExtValue(
//				"MIIFQjCCBCqgAwIBAgIKGzAAAAAAADwbuzANBgkqhkiG9w0BAQUFADBSMQswCQYDVQQGEwJDTjENMAsGA1UECgwEQkpDQTEYMBYGA1UECwwPUHVibGljIFRydXN0IENBMRowGAYDVQQDDBFQdWJsaWMgVHJ1c3QgQ0EtMTAeFw0xODA3MDExNjAwMDBaFw0xOTA3MDIxNTU5NTlaMIGUMQswCQYDVQQGEwJDTjEtMCsGA1UECgwk5YyX5Lqs5pWw5a2X6K6k6K+B6IKh5Lu95pyJ6ZmQ5YWs5Y+4MRIwEAYDVQQDDAnnjovmmKXml60xJTAjBgkqhkiG9w0BCQEWFndhbmdjaHVueHVAYmpjYS5vcmcuY24xGzAZBgoJkiaJk/IsZAEpDAsxODEzMTQ2NjkyMjCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAnwGb6P1G5y+aRkQkpw4vAdfK4GyFM+wLrgjWk+dbcyXbHEkYhDDdT5zYyJM5EqxOoeCtbKYwYtmCdegotSo+51cO69eiA81XNSs/qps8IL7YGHQLHPHEq5OPj3aKXScNU3Ene3MOcDjmSIFh0dfKJiM7vHJVC8m2mhzqMvCY9sUCAwEAAaOCAlkwggJVMB8GA1UdIwQYMBaAFKw77K8Mo1AO76+vtE9sO9vRV9KJMB0GA1UdDgQWBBTsbYVbPUdjW69afIDqoY6XobAYKjALBgNVHQ8EBAMCBsAwga8GA1UdHwSBpzCBpDBtoGugaaRnMGUxCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMRgwFgYDVQQLDA9QdWJsaWMgVHJ1c3QgQ0ExGjAYBgNVBAMMEVB1YmxpYyBUcnVzdCBDQS0xMREwDwYDVQQDEwhjYTNjcmwxOTAzoDGgL4YtaHR0cDovL2xkYXAuYmpjYS5vcmcuY24vY3JsL3B0Y2EvY2EzY3JsMTkuY3JsMAkGA1UdEwQCMAAwEQYJYIZIAYb4QgEBBAQDAgD/MB0GBSpWCwcBBBRTRjEzMDgyNzE5OTIxMjAzMjYxNTAdBgUqVgsHCAQUU0YxMzA4MjcxOTkyMTIwMzI2MTUwIAYIYIZIAYb4RAIEFFNGMTMwODI3MTk5MjEyMDMyNjE1MBsGCCpWhkgBgTABBA8xMDIwMDAwMDk4NTIyODEwJQYKKoEchu8yAgEEAQQXMUNAU0YxMzA4MjcxOTkyMTIwMzI2MTUwKgYLYIZIAWUDAgEwCQoEG2h0dHA6Ly9iamNhLm9yZy5jbi9iamNhLmNydDAPBgUqVhUBAQQGMTAwMDgwMEAGA1UdIAQ5MDcwNQYJKoEchu8yAgIBMCgwJgYIKwYBBQUHAgEWGmh0dHA6Ly93d3cuYmpjYS5vcmcuY24vY3BzMBMGCiqBHIbvMgIBAR4EBQwDNTIwMA0GCSqGSIb3DQEBBQUAA4IBAQB7n7oSI50PlNjB3H/tAPCOUXxMhiCuAsUgvBg4omKK2gbAelg5xCyDCo3HVExskfUnv7q3Qug9I06/YNDhlPyOzuCK4pLiXOTzmJwefzTKaqceHqEEkJfwWkI6IjSHEO8uNYUq6O9el3Swoj91rPUNdErYKhp9onbCs/Sw2G/45J5ZzY5WkCBYT0QgUYbZvQ7JUIj9+KTLhM0sv1Nhy/6JM81s6shy1uEG6UPXMHAlk+sJa20BmeXFb1p3pdN96B6OQPZv0XB8gPLdEbz4imQpU50AV1u2P6hVENUYOFnLX0hTxKfTlP0B+B3Vdc5Q4Kj1xUnLSktXgSIgnMJHhYMv",
//				"2.16.840.1.113732.2"));
		System.out.println(EncodeUtil.base64Encode(plain_data.getBytes("GBK")));
	}

}
