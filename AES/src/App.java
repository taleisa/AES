import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Type key in binary");
        String key  = input.nextLine();
        while(key.length()!=128){
            System.out.println("Key not 128 bits long, please retype key");
            key = input.nextLine();
        }
        String [] words = keyToWords(key);// Dividing key into 4 byte words
        for(int i=4;i<words.length;i++){// Start from 4 because we already have 4 words 0,1,2,3
            String temp = words[i-1];
            if(i%4==0){
                temp = rotateWord(temp);
                temp = substituteWord(temp);

            }


        }

    }
    //Method that will divide 128 bit key into 4 32 bit words
    private static String[] keyToWords(String key){
        String[] words = String[11];
        for(int i=0;i<key.length();i++){
            if(i<32)words[0]=words[0].concat(key.charAt(i));//The first 32 bits
            else if(i<64)words[1]=words[1].concat(key.charAt(i));//Bits 32 to 63
            else if(i<96)words[2]=words[2].concat(key.charAt(i));//Bits 64 to 95
            else if(i<128)words[3]=words[3].concat(key.charAt(i));//Bits 96 to 127
        }
        return words;

    }
    private static String rotateWord(String word){
        String rotatedWord;
        for(int i=1;i<word.length();i++){// Start with second character in original word(index 1)
            rotatedWord = rotatedWord.concat(word.charAt(i));
            if(i==word.length()-1)rotatedWord.concat(word.charAt(0));//Last iteration append first char
        }
        return rotatedWord;
    }
}
