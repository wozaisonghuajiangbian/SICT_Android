package com.dtr.sict.utils;

/**
 * Created by ace on 2017/7/20.
 */

public class UrlParser {

    private static String decrypt(String ciphertext)
    {
        int[] EncryList =
                {9,	8,	11	,10	,13 ,12, 15 , 14,
                        1,	0,   3	,2	, 5	,4 , 7  ,  6,
                        25,24,  27  ,26	,29 ,28, 31 , 30,
                        17,16,  19  ,18 ,21	,20, 23 , 22,
                        41,40,  43  ,42 ,45 ,44, 47 , 46,
                        33,32,  35  ,34 ,37 ,36, 39 , 38,
                        57,56,  59  ,58 ,61 ,60, 63 , 62,
                        49,48,	51  ,50 ,53 ,52, 55 , 54
                };

        char[] base64_enc_map =
                {
                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                        'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
                        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                        'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
                        '8', '9', '+', '/'
                };

        String dephertext = ciphertext;
        char [] tmp = ciphertext.toCharArray();


        for(int i = 0; i < ciphertext.length(); i++)
        {
            for (int j = 0; j < 64; j ++)
            {
                if (tmp[i] == base64_enc_map[j])
                {
                    tmp[i] = base64_enc_map[EncryList[j]];
                    break;
                }

            }
        }
        dephertext = String.valueOf(tmp);
        return dephertext;
    }

    public static String[] parseUrl(String url)
    {
        int index = url.indexOf('?');
        String parameter = url.substring(index + 1);
        String dephertext = decrypt(parameter.replace('$', '='));
        String[] arrPair = dephertext.split("&");

        for (int i = 0; i < arrPair.length; i++)
        {
            System.out.println(arrPair[i]);
        }

        return arrPair;
    }
}
