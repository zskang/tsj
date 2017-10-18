package cn.promore.bf.enrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/** * BASE64加密解密 */
@SuppressWarnings("restriction")
public class BASE64 {
	/** * BASE64解密 * @param key * @return * @throws Exception */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/** * BASE64加密 * @param key * @return * @throws Exception */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	public static void main(String[] args) throws Exception {
		String data = BASE64.encryptBASE64("authFor:站北社区,authExpire:2015-01-31".getBytes());
		System.out.println("加密：" + data);
/*		byte[] byteArray = BASE64.decryptBASE64(data);
		System.out.println("解密后：" + new String(byteArray));*/
	}
}