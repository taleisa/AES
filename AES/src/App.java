import java.net.SocketTimeoutException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    static HashMap<Integer,String> roundConstants = new HashMap<>();//Round constants to be used in key expansion
    static String[] roundkeys = new String[11];
    static int[][] box = {
        {0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76},
        {0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0},
        {0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15},
        {0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75},
        {0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84},
        {0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf},
        {0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8},
        {0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2},
        {0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73},
        {0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb},
        {0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79},
        {0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08},
        {0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a},
        {0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e},
        {0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf},
        {0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16}
    };
    public static void main(String[] args) {
        roundConstants.put(4, "00000001000000000000000000000000");//Round constant to be used at iteration 4 in key expansion
        roundConstants.put(8, "00000010000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(12, "00000100000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(16, "00001000000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(20, "00001010000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(24, "00010100000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(28, "00100110000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(32, "10000000000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(36, "00011011000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(40, "00110110000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        Scanner input = new Scanner(System.in);
        System.out.println("Type key in binary");
        String key  = input.nextLine();
        while(key.length()!=128){
            System.out.println("Key not 128 bits long, please retype key");
            key = input.nextLine();
        }
        roundkeys[0] = key;//Key is first round key
        String [] words = keyToWords(key);// Dividing key into 4 byte words
        for(int i=4;i<44;i++){// Start from 4 because we already have 4 words 0,1,2,3
            String temp = words[i-1];
            System.out.println(temp+" "+i);
            if(i%4==0){
                temp = rotateWord(temp);
                temp = substituteWord(temp);
                BitSet result = toBitSet(temp);
                result.xor(toBitSet(roundConstants.get(i)));
                temp = fromBitSetToString(result);//Using the toBitSet method to make xor easier then converting back to string
                //Removing characters at beggining and end of string that will cause problems
                temp = temp.replace("{", "");
                temp = temp.replace("}", "");
            }
            BitSet result = toBitSet(temp);
            System.out.println("After to Bitset method"+result);
            System.out.println("Temp that was sent to bitset method"+temp);
            result.xor(toBitSet(words[i-4]));
            System.out.println("Result after xor with rcon"+result);
            temp = fromBitSetToString(result);//Method to change bit string to bit set for ease of xor operation
            //Removing characters at beggining and end of string that will cause problems
            temp = temp.replace("{", "");
            temp = temp.replace("}", "");
            System.out.println("Saving "+ temp);
            System.out.println("----------------------------------------------"+i/4);
            words[i] = temp;
            if(roundkeys[i/4]==null)roundkeys[i/4]="";
            roundkeys[i/4] = roundkeys[i/4].concat(temp);

        }
        for(int i=0;i<roundkeys.length;i++){
            System.out.println("Key "+i+" "+roundkeys[i]);
        }
        



    }






    //Method that will divide 128 bit key into 4 32 bit words
    private static String[] keyToWords(String key){
        String[] words = new String[44];
        words[0] = "";
        words[1] = "";
        words[2] = "";
        words[3] = "";
        for(int i=0;i<key.length();i++){
            if(i<32)words[0]=words[0].concat(key.charAt(i)+"");//The first 32 bits
            else if(i<64)words[1]=words[1].concat(key.charAt(i)+"");//Bits 32 to 63
            else if(i<96)words[2]=words[2].concat(key.charAt(i)+"");//Bits 64 to 95
            else if(i<128)words[3]=words[3].concat(key.charAt(i)+"");//Bits 96 to 127
        }
        return words;

    }
    private static String rotateWord(String word){
        String rotatedWord = "";
        for(int i=8;i<word.length();i++){// Start with 9th character in original word(index 8)
            rotatedWord = rotatedWord.concat(word.charAt(i)+"");
        }
        for(int j=0;j<8;j++){
            rotatedWord = rotatedWord.concat(word.charAt(j)+"");//Last iteration append first 8 characters
        }
        return rotatedWord;
    }
    private static String substituteWord(String word){
        String substitutedWord = "";
        //System.out.println(word);
        for(int i=0;i<4;i++){
            String strRow = word.substring(i*8,i*8+4);//4 bits , 1 hexa character or 1 nibble that represent the row as a string(in binary)
            int row = Integer.parseInt(strRow,2);//Integer representation of strRow
            String strColumn = word.substring(i*8+4,(i*8)+8);//4 bits , 1 hexa character or 1 nibble that represent the column as a string(in binary)
            int column = Integer.parseInt(strColumn,2);//Integer representation of strColumn
            String resultFromSbox = Integer.toBinaryString(box[row][column]);//Parsing result from s box to bit string
            resultFromSbox = String.format("%8s", resultFromSbox).replace(' ', '0');//To add leading zeros to make it  8 bits
            substitutedWord = substitutedWord.concat(resultFromSbox);
        }
        return substitutedWord;

    }
    //Method to change bit string to bit set
    private static BitSet toBitSet(String bitString){
        BitSet bitSet = new BitSet(bitString.length());//Bitset with same length as bit string. Default value is 0 for all bits
        for(int i=0;i<bitString.length();i++){
            if(bitString.charAt(i)=='1')//If charat(i) in bitstring = 1 set ith bit in bit set
                bitSet.set(i);
        }
        return bitSet;
    }
    private static String fromBitSetToString(BitSet bitset){
        String bitString = "";
        for(int i=0;i<32;i++){
            if(bitset.get(i))bitString = bitString.concat("1");
            else bitString = bitString.concat("0");
        }
        return bitString;

    }
}
