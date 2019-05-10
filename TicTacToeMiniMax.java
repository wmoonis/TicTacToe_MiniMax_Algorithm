//Wajiha Moonis

import java.util.*;

public class TicTacToeMiniMax {
public static void main(String[] args) { 
        Board b = new Board();
        Random num = new Random();
        int selection;
        b.printBoard();
        Scanner kb = new Scanner(System.in);
    do{
        System.out.println("Please make a selection: Enter 1 for Machine OR  Enter 2 for User ");
        selection = kb.nextInt();
        }while(selection!=1 && selection!=2);
 
        if (selection == 1) {
            Position p = new Position(num.nextInt(3), num.nextInt(3));
            b.nextTurn(p, 1);
            b.printBoard();
        }
        Scanner kb2 = new Scanner(System.in);
        
        do {b.printBoard();
            String s="";
            System.out.println("Your Turn! Enter position (for example: 01 or 11): ");
            s = kb2.nextLine();
            if(s.length()==2){
            
            int x = Integer.parseInt(s.substring(0,1));
            int y = Integer.parseInt(s.substring(1));
            
            if(x>-1 && x<3 && y>-1 && y<3){
            Position userTurn = new Position(x,y);

            b.nextTurn(userTurn, 2); 
            b.printBoard();
            if (b.endGame()) break;
            
            b.alphaBeta(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 1);
            for (PointsTracker pas : b.child) 
            b.nextTurn(b.optimalSol(), 1);
            
        }}
        else System.out.println("Invalid Entry. Try Again.");
        
        
        }while (!b.endGame());
    
        
        
        
        
        if (b.xWins()) {
            System.out.println("You Lose");
        } else if (b.oWins()) {
            System.out.println("You Win");
        } else {
            System.out.println("Draw!");
        }
        
        
    }
}




class Position {

    int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

}

class PointsTracker {

    int value;
    Position p;

    PointsTracker(int value, Position p) {
        this.value = value;
        this.p = p;
    }
}

class Board {

    List<Position> initial;
    
    int[][] board = new int[3][3]; 

    List<PointsTracker> child = new ArrayList<>();


     public List<Position> openPositions() {
        initial = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == 0) {
                    initial.add(new Position(row, col));
                }
            }
        }
        return initial;
    }

  
    public int key(int X, int O){
        int k;
        if (X == 3)                 k = 3;
         else if (X == 2 && O == 0) k = 2;
         else if (X == 1 && O == 0) k = 1;
         else if (O == 3)           k = -3;
         else if (O == 2 && X == 0) k = -2;
         else if (O == 1 && X == 0) k = -1;
         else                       k = 0;
       
       
               return k;
    }

    public int evaluation() {
        int point = 0;
        int empty = 0;
        
        
        for (int col = 0; col < 3; ++col) {
            
            int X = 0;
            int O = 0;
            for (int row = 0; row < 3; ++row) {
                if (board[row][col] == 0) {
                    empty++;
                } else if (board[row][col] == 1) {
                    X++;
                } else {
                    O++;
                } 
            }
           point+=key(X, O);
        }

        
        for (int row = 0; row < 3; ++row) {
            empty = 0; int X = 0;  int O = 0;
            for (int col = 0; col < 3; ++col) {
                if (board[row][col] == 0) {
                    empty++;
                } else if (board[row][col] == 1) {
                    X++;
                } else {
                    O++;
                }

            } 
            point+=key(X, O); 
        }

        
        empty= 0;
        int X = 0;
        int O = 0;

  
        for (int row = 0, col = 0; row < 3; ++row, ++col) {
            if (board[row][col] == 1) {
                X++;
            } else if (board[row][col] == 2) {
                O++;
            } else {
                empty++;
            }
        }

        point+=key(X, O);

        empty = 0;
        X = 0;
        O = 0;

        
        for (int row = 2, col = 0; row > -1; --row, ++col) {
            if (board[row][col] == 1) {
                X++;
            } else if (board[row][col] == 2) {
                O++;
            } else {
                empty++;
            }
        }

        point+=key(X, O);

        return point;
    }
    
    
 
    
  
    
    
    public void printBoard() {
        System.out.println();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
               if(board[row][col]==1)
                System.out.print(" X ");
                else if(board[row][col]==2)
                System.out.print(" O ");
                else{
                System.out.print(row);System.out.print(col+" ");}
                
            }
            System.out.println();

        }
    } 
    
    //1=Machine & 2=User
    public void nextTurn(Position position, int oneORtwo) {
        board[position.x][position.y] = oneORtwo;   
    }
    
    
//User Wins ; O is for User
    public boolean oWins() {
        if ((board[0][0] == 2)&&(board[0][0] == board[1][1]) &&(board[0][0] == board[2][2]))           
            return true;
            
        
        else if((board[0][2] == 2)&&(board[0][2] == board[1][1]) && (board[0][2] == board[2][0]) )
            return true;
        
        for (int r = 0; r < 3; r++) {
            if (((board[r][0] == 2) && (board[r][0] == board[r][1]) && (board[r][0] == board[r][2]))
                    || (board[0][r] == board[1][r] && board[0][r] == board[2][r] && board[0][r] == 2)) {
                
                return true;
            }
        }

        return false;
    }

 
//Machine wins; X is for Machine
    public boolean xWins() {
        for (int i = 0; i < 3; ++i) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
                
                return true;
            }
       
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
            
            return true;
        }
       
        }
        return false;
    }

 public boolean endGame() {
        
        return( oWins() || openPositions().isEmpty()|| xWins());
         
         
         
    }
  
  

    public Position optimalSol() {
        int alpha = -10000;
        int beta = 10000;

        for (int c = 0; c < child.size(); ++c) {
            if (alpha < child.get(c).value) {
                alpha = child.get(c).value;
                beta = c;
            }
        }

        return child.get(beta).p;
    }

  

 int d2= -1;
    public int alphaBeta(int a, int b, int d, int m){
       
        if(b<=a)
        
        { 
        if(m == 1)
             return Integer.MAX_VALUE;
        else 
            return Integer.MIN_VALUE; }
        
        if(d == d2 || endGame())
          return evaluation();
        
        List<Position> open = openPositions();
        
        if(open.isEmpty()) 
         return 0;
        
        if(d==0) 
         child.clear(); 
        
        int maxValue = Integer.MIN_VALUE, minValue = Integer.MAX_VALUE;
        
        for(int i=0;i<open.size(); ++i){
            Position p = open.get(i);
            
            int current = 0;
            
            if(m == 1){
                nextTurn(p, 1); 
                current = alphaBeta(a, b, (d+1), 2);
                maxValue = Math.max(maxValue, current); 
                
                
                a = Math.max(current, a);
                
                if(d == 0)
                    child.add(new PointsTracker(current,p));
            }else if(m == 2){
                nextTurn(p, 2);
                current = alphaBeta(a, b,(d+1), 1); 
                minValue = Math.min(minValue, current);
                
          
                b = Math.min(current, b);
            }
            
            board[p.x][p.y] = 0; 
            
            
            if(current == Integer.MAX_VALUE || current == Integer.MIN_VALUE) break;
        }
        return m == 1 ? maxValue : minValue;
    }  

     

 

}



