import java.io.*;

public class lexer {
    public static void main(String[] args) throws Exception{
        File in = new File(args[0]);
        Reader r = new FileReader(in);
        BufferedReader br = new BufferedReader(r);
        String line = br.readLine();
        String text = "";
        while(line!=null) {
            text += line + " ";
            line = br.readLine();
        }
        String[] tokens = text.split(" +");
        for(int i = 0; i < tokens.length; i++){
//            System.out.println(tokens[i]);
            try {
                token_analysis(tokens[i]);
            }catch (Exception e){
                System.out.println("Unknown");
                break;
            }
        }
        br.close();
        r.close();
    }

    static void printnum(String token){
        int tempnum = Integer.parseInt(token);
        System.out.println("Int("+tempnum+")");
    }

    static void printkey(String token) throws Exception{
        switch (token){
            case "BEGIN":
                System.out.println("Begin");
                break;
            case "END":
                System.out.println("End");
                break;
            case "FOR":
                System.out.println("For");
                break;
            case "IF":
                System.out.println("If");
                break;
            case "THEN":
                System.out.println("Then");
                break;
            case "ELSE":
                System.out.println("Else");
                break;
            default:
                if(token.matches("^[a-zA-Z][0-9a-zA-Z]*$"))
                System.out.println("Ident("+token+")");
                else
                    throw new Exception();
        }
    }

    static void token_analysis(String token) throws Exception {
        int i;
        if (token.equals(""))
            return;
        if (Character.isDigit(token.charAt(0))) {
            for (i = 0; i < token.length(); i++) {
                if (!Character.isDigit(token.charAt(i))) {
                    String tempnum = token.substring(0, i);
                    printnum(tempnum);
                    String other = token.substring(i);
                    token_analysis(other);
                    break;
                }
            }
            if(i==token.length())
                printnum(token);
        } else if (Character.isLetter(token.charAt(0))) {
            for (i = 1; i < token.length(); i++) {
                if (!Character.isLetterOrDigit(token.charAt(i))) {
                    String temp = token.substring(0, i);
                    printkey(temp);
                    String other = token.substring(i);
                    token_analysis(other);
                    break;
                }
            }
            if(i==token.length())
                printkey(token);
        } else {
            String other;
            switch (token.substring(0,1)) {
                case ":":
                    if(token.length()==1||token.charAt(1)!='=') {
                        System.out.println("Colon");
                        other = token.substring(1);
                        token_analysis(other);
                    }else {
                        System.out.println("Assign");
                        other = token.substring(2);
                        token_analysis(other);
                    }
                    break;
                case "+":
                    System.out.println("Plus");
                    other = token.substring(1);
                    token_analysis(other);
                    break;
                case "*":
                    System.out.println("Star");
                    other = token.substring(1);
                    token_analysis(other);
                    break;
                case ",":
                    System.out.println("Comma");
                    other = token.substring(1);
                    token_analysis(other);
                    break;
                case "(":
                    System.out.println("LParenthesis");
                    other = token.substring(1);
                    token_analysis(other);
                    break;
                case ")":
                    System.out.println("RParenthesis");
                    other = token.substring(1);
                    token_analysis(other);
                    break;
                default:
                    throw new Exception();
            }
        }
    }
}
