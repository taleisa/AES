import java.util.Scanner;

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

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Type text in hex");
        String text = input.nextLine();

        System.out.println(subBytes(text));
    }

    public static String subBytes(String text){
        char[] stateMatrix = text.toCharArray(); //Converting string to char array to easily manipulate.
        String newStateMatrix[] = {
                sBox[hexToDec(stateMatrix[0])][hexToDec(stateMatrix[1])],sBox[hexToDec(stateMatrix[2])][hexToDec(stateMatrix[3])],sBox[hexToDec(stateMatrix[4])][hexToDec(stateMatrix[5])],sBox[hexToDec(stateMatrix[6])][hexToDec(stateMatrix[7])],
                sBox[hexToDec(stateMatrix[8])][hexToDec(stateMatrix[9])],sBox[hexToDec(stateMatrix[10])][hexToDec(stateMatrix[11])],sBox[hexToDec(stateMatrix[12])][hexToDec(stateMatrix[13])],sBox[hexToDec(stateMatrix[14])][hexToDec(stateMatrix[15])],
                sBox[hexToDec(stateMatrix[16])][hexToDec(stateMatrix[17])],sBox[hexToDec(stateMatrix[18])][hexToDec(stateMatrix[19])],sBox[hexToDec(stateMatrix[20])][hexToDec(stateMatrix[21])],sBox[hexToDec(stateMatrix[22])][hexToDec(stateMatrix[23])],
                sBox[hexToDec(stateMatrix[24])][hexToDec(stateMatrix[25])],sBox[hexToDec(stateMatrix[26])][hexToDec(stateMatrix[27])],sBox[hexToDec(stateMatrix[28])][hexToDec(stateMatrix[29])],sBox[hexToDec(stateMatrix[30])][hexToDec(stateMatrix[31])]};
        String res = String.join("", newStateMatrix);
        return res;
    }

    public static int hexToDec(char fourBits){ //converting hex to decimal to have rows and cols to obtain value from s-box
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

    private static String arrayToString(char[] array) {// Method that converts char array to string
        String newString = "";
        for (char bit : array) {// Iterating through the char array
            newString = newString.concat(bit + "");
        }
        return newString;
    }
}
