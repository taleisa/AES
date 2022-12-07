import java.util.Scanner;
import java.math.BigInteger;
import java.util.BitSet;
import java.util.HashMap;

public class App {
    static String[][] sBox = {
            {"63","7c","77","7b","f2","6b","6f","c5","30","01","67","2b","fe","d7","ab","76"},
            {"ca","82","c9","7d","fa","59","47","f0","ad","d4","a2","af","9c","a4","72","c0"},
            {"b7","fd","93","26","36","3f","f7","cc","34","a5","e5","f1","71","d8","31","15"},
            {"04","c7","23","c3","18","96","05","9a","07","12","80","e2","eb","27","b2","75"},
            {"09","83","2c","1a","1b","6e","5a","a0","52","3b","d6","b3","29","e3","2f","84"},
            {"53","d1","00","ed","20","fc","b1","5b","6a","cb","be","39","4a","4c","58","cf"},
            {"d0","ef","aa","fb","43","4d","33","85","45","f9","02","7f","50","3c","9f","a8"},
            {"51","a3","40","8f","92","9d","38","f5","bc","b6","da","21","10","ff","f3","d2"},
            {"cd","0c","13","ec","5f","97","44","17","c4","a7","7e","3d","64","5d","19","73"},
            {"60","81","4f","dc","22","2a","90","88","46","ee","b8","14","de","5e","0b","db"},
            {"e0","32","3a","0a","49","06","24","5c","c2","d3","ac","62","91","95","e4","79"},
            {"e7","c8","37","6d","8d","d5","4e","a9","6c","56","f4","ea","65","7a","ae","08"},
            {"ba","78","25","2e","1c","a6","b4","c6","e8","dd","74","1f","4b","bd","8b","8a"},
            {"70","3e","b5","66","48","03","f6","0e","61","35","57","b9","86","c1","1d","9e"},
            {"63","7c","77","7b","f2","6b","6f","c5","30","01","67","2b","fe","d7","ab","76"},
            {"63","7c","77","7b","f2","6b","6f","c5","30","01","67","2b","fe","d7","ab","76"}
    };

    static int dec; //Used in hexToDec()
    
    static HashMap<Integer,String> roundConstants = new HashMap<>();//Round constants to be used in key expansion
    //static String[] roundkeys = new String[11];
    static String[] roundKeysInBinary = new String[11];
    static String[] roundKeysInHex = new String[11];
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
    public static void main(String[] args) throws Exception {
        roundConstants.put(4, "00000001000000000000000000000000");//Round constant to be used at iteration 4 in key expansion
        roundConstants.put(8, "00000010000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(12, "00000100000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(16, "00001000000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(20, "00010000000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(24, "00100000000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(28, "01000000000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(32, "10000000000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(36, "00011011000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        roundConstants.put(40, "00110110000000000000000000000000");//Round constant to be used at iteration 8 in key expansion
        Scanner input = new Scanner(System.in);
        System.out.println("Type text (In Hex):");
        String text = input.nextLine();
        System.out.println("Type key (In Hex):");
        String key  = input.nextLine();
        key = hexToBinary(key);
        roundKeysInBinary[0] = key;//Key is first round key
        String [] words = keyToWords(key);// Dividing key into 4 byte words
        for(int i=4;i<44;i++){// Start from 4 because we already have 4 words 0,1,2,3
            String temp = words[i-1];
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
            result.xor(toBitSet(words[i-4]));
            temp = fromBitSetToString(result);//Method to change bit string to bit set for ease of xor operation
            //Removing characters at beggining and end of string that will cause problems
            temp = temp.replace("{", "");
            temp = temp.replace("}", "");
            words[i] = temp;
            if(roundKeysInBinary[i/4]==null)roundKeysInBinary[i/4]="";
            roundKeysInBinary[i/4] = roundKeysInBinary[i/4].concat(temp);
        }
        //Converting round keys from binary to hexa
        for (int i=0; i<roundKeysInBinary.length; i++){
            StringBuffer tempp = new StringBuffer(110);
            for (int j=0; j<roundKeysInBinary[i].length(); j+=4){
                tempp.append(binaryToHex(roundKeysInBinary[i].substring(j,j+4))); //converting every 4 bits into Hex.
            }
            roundKeysInHex[i] = tempp.toString();
        }
        System.out.println();
        for(int i=0;i<roundKeysInHex.length;i++){
            System.out.println("Round Key "+i+": "+roundKeysInHex[i]);
        }

        String addRoundKeyOutput = addRoundKey(text,roundKeysInHex[0]); //Calling add round key
        System.out.println("\nAdd Round Key - Round 0 \n"+text+" XOR "+roundKeysInHex[0]+" = "+addRoundKeyOutput);

        for (int i=1; i<=10; i++){ //10 Rounds
            System.out.println("\n---------------------------------------------- Round "+i+" ----------------------------------------------");
            String subBytesOutput = subBytes(addRoundKeyOutput);
            System.out.println("Substitution Bytes - Round "+i+" \n"+addRoundKeyOutput+" After Substitution = "+subBytesOutput);

            String shiftRowsOutput = shiftRow(subBytesOutput);
            System.out.println("\nShift Row - Round "+i+" \n"+subBytesOutput+" After Shifting = "+shiftRowsOutput);

            //String mixColsOutput = mixColumns(shiftRowsOutput);
            //System.out.println("\nMix Column - Round "+i+" \n"+shiftRowsOutput+" Multiplied by fixed matrix = "+mixColsOutput);

            //addRoundKeyOutput = addRoundKey(mixColsOutput,roundKeysInHex[i]);
            //System.out.println("\nAdd Round Key - Round "+i+" \n"+mixColsOutput+" XOR "+roundKeysInHex[i]+" = "+addRoundKeyOutput);
            if (i==10){
                System.out.println("\nCiphertext : "+addRoundKeyOutput);
            }
        }
    }

    private static String mixColumns(String text) throws Exception{
        int[][] stateMatrix = populateStateMatrix(text);//State matrix that contains message in hexadecimal
        String stringStateMatrix = "";
        for(int i=0;i<4;i++){
            stateMatrix = columnMultiplication(i,stateMatrix);//Update each column after performing neccessary calculations
        }
        for(int[]column:stateMatrix){
            for(int hex:column){
                String result = String.format("%2s", Integer.toHexString(hex)).replace(" ", "0");
                stringStateMatrix = stringStateMatrix.concat(result);
            }
        }
        System.out.println(stringStateMatrix);
        return stringStateMatrix;
    }

    //Method that will recieve string of hexa and return 4x4 matrix of hexadecimal
    private static int[][] populateStateMatrix(String text){
        int[][] stateMatrix = new int[4][4];
        for(int i=0;i<text.length();i+=2){
            if(i<8){
                stateMatrix[0][i/2]=Integer.parseInt("0"+text.charAt(i)+text.charAt(i+1), 16);
            }else if(i<16){
                stateMatrix[1][(i-8)/2]=Integer.parseInt("0"+text.charAt(i)+text.charAt(i+1), 16);
            }else if(i<24){
                stateMatrix[2][(i-16)/2]=Integer.parseInt("0"+text.charAt(i)+text.charAt(i+1), 16);
            }else if(i<32){
                stateMatrix[3][(i-24)/2]=Integer.parseInt("0"+text.charAt(i)+text.charAt(i+1), 16);
            }
        }
        return stateMatrix;
    }
    private static int[][] columnMultiplication(int column,int[][] stateMatrix) throws Exception{
        //First hex in updated column
        int firstElement = multiply(0x02, stateMatrix[0][column])^multiply(0x03, stateMatrix[1][column])^multiply(0x01, stateMatrix[2][column])^multiply(0x01, stateMatrix[3][column]);
        //Second hex in updated column
        int secondElement = multiply(0x01, stateMatrix[0][column])^multiply(0x02, stateMatrix[1][column])^multiply(0x03, stateMatrix[2][column])^multiply(0x01, stateMatrix[3][column]);
        //Third hex in updated column
        int thirdElement = multiply(0x01, stateMatrix[0][column])^multiply(0x01, stateMatrix[1][column])^multiply(0x02, stateMatrix[2][column])^multiply(0x03, stateMatrix[3][column]);
        //Fourth hex in updated column
        int fourthElement = multiply(0x03, stateMatrix[0][column])^multiply(0x01, stateMatrix[1][column])^multiply(0x01, stateMatrix[2][column])^multiply(0x02, stateMatrix[3][column]);
        stateMatrix[0][column] = firstElement;
        stateMatrix[1][column] = secondElement;
        stateMatrix[2][column] = thirdElement;
        stateMatrix[3][column] = fourthElement;
        return stateMatrix;
        
    }
    private static int multiply(int fixedMatrix,int stateMatrix) throws Exception{
        if(fixedMatrix==0x01)return stateMatrix;//When multiplying with one we do nothing
        else if(fixedMatrix==0x02){
            char mostSignificantBit = String.format("%8s", Integer.toBinaryString(stateMatrix)).replace(' ', '0').charAt(0);
            stateMatrix = leftShift(stateMatrix);
            if(mostSignificantBit=='1'){
                stateMatrix = (stateMatrix^0x1B);
            }else if(mostSignificantBit!='0')throw new Exception("Most sig bit not 0 or 1");

        }else if(fixedMatrix==0x03){
            char mostSignificantBit = String.format("%8s", Integer.toBinaryString(stateMatrix)).replace(' ', '0').charAt(0);
            int leftstateMatrix = leftShift(stateMatrix);
            if(mostSignificantBit=='1'){
                leftstateMatrix = (leftstateMatrix^0x1b);
            }else if(mostSignificantBit!='0')throw new Exception("Most sig bit not 0 or 1");
            stateMatrix = (stateMatrix^leftstateMatrix);
        }
        return stateMatrix;

    }
    private static int leftShift(int hex){
        char[] bits = String.format("%8s", Integer.toBinaryString(hex)).replace(' ', '0').toCharArray();
        for(int i=0;i<bits.length;i++){
            if(i==bits.length-1){//Last bit is zero
                bits[i] = '0';
                break;
            }
            bits[i] = bits[i+1];//Left shift each bit by 1
        }
        
        String bitString = new String(bits);
        return Integer.parseInt(bitString,2);

    }

    private static String subBytes(String text){
        char[] stateMatrix = text.toCharArray(); //Converting string to char array to easily manipulate.
        String newStateMatrix[] = {
                sBox[hexToDec(stateMatrix[0])][hexToDec(stateMatrix[1])],sBox[hexToDec(stateMatrix[2])][hexToDec(stateMatrix[3])],sBox[hexToDec(stateMatrix[4])][hexToDec(stateMatrix[5])],sBox[hexToDec(stateMatrix[6])][hexToDec(stateMatrix[7])],
                sBox[hexToDec(stateMatrix[8])][hexToDec(stateMatrix[9])],sBox[hexToDec(stateMatrix[10])][hexToDec(stateMatrix[11])],sBox[hexToDec(stateMatrix[12])][hexToDec(stateMatrix[13])],sBox[hexToDec(stateMatrix[14])][hexToDec(stateMatrix[15])],
                sBox[hexToDec(stateMatrix[16])][hexToDec(stateMatrix[17])],sBox[hexToDec(stateMatrix[18])][hexToDec(stateMatrix[19])],sBox[hexToDec(stateMatrix[20])][hexToDec(stateMatrix[21])],sBox[hexToDec(stateMatrix[22])][hexToDec(stateMatrix[23])],
                sBox[hexToDec(stateMatrix[24])][hexToDec(stateMatrix[25])],sBox[hexToDec(stateMatrix[26])][hexToDec(stateMatrix[27])],sBox[hexToDec(stateMatrix[28])][hexToDec(stateMatrix[29])],sBox[hexToDec(stateMatrix[30])][hexToDec(stateMatrix[31])]};
        String res = String.join("", newStateMatrix);
        return res;
    }

    private static int hexToDec(char fourBits){ //converting hex to decimal to have rows and cols to obtain value from s-box
        if (fourBits == 'a' || fourBits == 'b' || fourBits == 'c' || fourBits == 'd' || fourBits == 'e' || fourBits == 'f'){
            switch (fourBits) { //converting hex to dec to use it in rows and cols
                case 'a':
                    dec = 10;
                    break;
                case 'b':
                    dec = 11;
                    break;
                case 'c':
                    dec = 12;
                    break;
                case 'd':
                    dec = 13;
                    break;
                case 'e':
                    dec = 14;
                    break;
                case 'f':
                    dec = 15;
                    break;
            }
        } else {
            dec = Character.getNumericValue(fourBits); //if the char is a number then just convert it to int
        }
        return dec;
    }

    private static String addRoundKey(String text, String key){
        char[] textArr = text.toCharArray();
        char[] keyArr = key.toCharArray();
        String temp;
        StringBuffer output = new StringBuffer(110);
        for (int i=0; i<text.length(); i++){
            temp = binaryToHex(xor(hexToBinary(textArr[i]),hexToBinary(keyArr[i]))); //converting text and key to binary then XORing them, then converting the result back to hexa.
            output.append(temp);
        }
        return output.toString();
    }

    private static String xor(String x, String y) {
        String result = "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < x.length(); i++) {
            sb.append(x.charAt(i) ^ y.charAt(i));
        }
        result = sb.toString();
        return result;
    }

    private static String hexToBinary(char hex){
        int temp = Integer.parseInt(hex+"", 16); // radix 16 for hex
        String binary = String.format("%4s", Integer.toBinaryString(temp)).replace(" ", "0"); //padding zeros to the left to make sure it is 4-bit
        return binary;
    }

    private static String binaryToHex(String binary){
        int temp = Integer.parseInt(binary, 2); // radix 2 for binary
        String hexa = Integer.toHexString(temp);
        return hexa;
    }
    private static String hexToBinary(String hex){
        String binary = String.format("%128s", new BigInteger(hex, 16).toString(2)).replace(" ", "0"); //padding zeros to the left to make sure it is 128-bit
        return binary;
    }

    private static String shiftRow(String text){
        char[] stateMatrix = text.toCharArray(); //Converting string to char array to easily manipulate.
        String newStateMatrix[] = {
                stateMatrix[0]+"",stateMatrix[1]+"",stateMatrix[2]+"",stateMatrix[3]+"",stateMatrix[4]+"",stateMatrix[5]+"",stateMatrix[6]+"",stateMatrix[7]+"",
                stateMatrix[10]+"",stateMatrix[11]+"",stateMatrix[12]+"",stateMatrix[13]+"",stateMatrix[14]+"",stateMatrix[15]+"",stateMatrix[8]+"",stateMatrix[9]+"",
                stateMatrix[20]+"",stateMatrix[21]+"",stateMatrix[22]+"",stateMatrix[23]+"",stateMatrix[16]+"",stateMatrix[17]+"",stateMatrix[18]+"",stateMatrix[19]+"",
                stateMatrix[30]+"",stateMatrix[31]+"",stateMatrix[24]+"",stateMatrix[25]+"",stateMatrix[26]+"",stateMatrix[27]+"",stateMatrix[28]+"",stateMatrix[29]+""
        };

        String res = String.join("", newStateMatrix);
        return res;
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
