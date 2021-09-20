public class Reverse{
    public static void main(String[] args) {
      
        String name="My name is ABC";
 
        String count[] = name.split(" ");
        String reversedString = "";
 
        for (int i = 0; i < count.length; i++) { 
            if (i == count.length - 1) 
                reversedString = count[i] + reversedString; 
            else
                reversedString = " " + count[i] + reversedString; 
        } 
 
        System.out.print("Reversed string : " + reversedString);
    }
}