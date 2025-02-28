package com.louie.labwork3.Chess;

import Figures.*;

import java.util.ArrayList;

public class Board
{
    // Текущий цвет (очередь хода)
    private char colorGame;

    // = Геттер и сеттер цвета =
    public void setColorGame(char colorGame)
    {
        this.colorGame = colorGame;
    }

    public char getColorGame()
    {
        return colorGame;
    }

    // Поле
    private Figure fields[][] = new Figure[8][8];
    private ArrayList<String> takeWhite = new ArrayList(16);
    private ArrayList<String> takeBlack = new ArrayList(16);
    
    // = Геттеры =
    public ArrayList<String> getTakeBlack()
    {
        return takeBlack;
    }

    public ArrayList<String> getTakeWhite()
    {
        return takeWhite;
    }

    // = Функция инициализации =
    public void init()
    {
        this.fields[0] = new Figure[]{
                new Rook("R", 'w'), new Knight("N", 'w'), new Bishop("B", 'w'),
                new Queen("Q", 'w'), new King("K", 'w'), new Bishop("B", 'w'),
                new Knight("N", 'w'), new Rook("R",'w')
        };
        
        this.fields[1] = new Figure[]{
                new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),
                new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),
        };

        this.fields[6] = new Figure[] {
                new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b'),
                new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b')
        };

        this.fields[7] = new Figure[]{
                new Rook("R", 'b'), new Knight("N", 'b'), new Bishop("B", 'b'),
                new Queen("Q", 'b'), new King("K", 'b'), new Bishop("B", 'b'),
                new Knight("N", 'b'), new Rook("R",'b')
        };
    }

    // = Функция получения клетки =
    public String getCell(int row, int col)
    {
        Figure figure = this.fields[row][col];
        
        if (figure ==null)
        {
            return "    ";
        }
        
        return  " "+figure.getColor()+figure.getName()+" ";
    }
    
    // = Функция отрисовки доски =
    public void print_board()
    {
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for (int row = 7; row > -1 ; row --){
            System.out.print(row);
            for (int col=0; col<8; col++){
                System.out.print("|"+getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for(int col=0; col< 8; col++){
            System.out.print("    "+col);
        }
    }

    // = Функция проверки возможности движения =
    public boolean canMoveOnBoard(int row, int col, int row1, int col1)
    {
        if (this.fields[row1][col1].getName().equals("К"))
        {
            return false;
        }
        
        String step_figure = this.fields[row1][col1].getName();
        
        if (step_figure.equals("R"))
        {
            if (row - row1 != 0)
            {
                int min_r = Math.min(row, row1);
                int max_r = Math.max(row, row1);
                for (int i = min_r + 1; i != max_r; i++)
                {
                    if (this.fields[i][col] != null)
                    {
                        return false;
                    }
                }
            }
            else
            {
                int min_c = Math.min(col, col1);
                int max_c = Math.max(col, col1);
                for (int i = min_c + 1; i != max_c; i++)
                {
                    if (this.fields[row][i] != null)
                    {
                        return false;
                    }
                }
            }
        }
        else if (step_figure.equals("B"))
        {
            int min_r = Math.min(row, row1);
            int max_r = Math.max(row, row1);
            int min_c = Math.min(col, col1);
            for (int i = min_r + 1; i != max_r; i++)
            {
                min_c += 1;
                if (this.fields[i][min_c] != null)
                {
                        return false;
                }
            }
        }
        else if (step_figure.equals("Q"))
        {
            int min_r = Math.min(row, row1);
            int max_r = Math.max(row, row1);
            int min_c = Math.min(col, col1);
            int max_c = Math.max(col, col1);
            while(min_r != max_r || min_c != max_c)
            {
                if (min_r != max_r)
                {
                    min_r += 1;
                }
                
                if (min_c != max_c)
                {
                    min_c += 1;
                }
                
                if (this.fields[min_r][min_c] != null)
                {
                    return false;
                }
            }
        }
        else if (step_figure.equals("P"))
        {
            if (row1 - row == 2)
            {
                if (this.fields[row + 1][col] != null)
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public int[] getKing()
    {
        int k_row = 0;
        int k_col = 0;
        
        for (int i = 0; i != 8; i++)
        {
            boolean found = false;
            for (int j = 0; j != 8; j++)
            {
                if (this.fields[i][j].getName().equals("K"))
                {
                    if (this.fields[i][j].getColor() != step)
                    {
                        k_row = i;
                        k_col = j;
                        found = true;
                        break;
                    }
                }
            }
            
            if (found)
            {
                break;
            }
        }
        
        int coords[] = new int[2];
        coords[0] = k_row;
        coords[1] = k_col;
        return coords;
    }
    
    // = Функция проверки на шах =
    public boolean isCheck(int f_row, int f_col)
    {
        int k_row = 0;
        int k_col = 0;
        char step = getColorGame();
        
        for (int i = 0; i != 8; i++)
        {
            boolean found = false;
            for (int j = 0; j != 8; j++)
            {
                if (this.fields[i][j].getName().equals("K"))
                {
                    if (this.fields[i][j].getColor() != step)
                    {
                        k_row = i;
                        k_col = j;
                        found = true;
                        break;
                    }
                }
            }
            
            if (found)
            {
                break;
            }
        }
        
        Figure figure = this.fields[f_row][f_col];
        if (canMoveOnBoard(f_row, f_col, k_row, k_col) && figure.canMove(f_row, f_col, k_row, k_col))
        {
                return true;
        }
        
        return false;
    }
    
    // = Функция проверки на мат =
    public boolean isMate(int f_row, int f_col)
    {
        if (isCheck(f_row, f_col))
        {
            int k_row = 0;
            int k_col = 0;
            char step = getColorGame();
        
            for (int i = 0; i != 8; i++)
            {
                boolean found = false;
                for (int j = 0; j != 8; j++)
                {
                    if (this.fields[i][j].getName().equals("K"))
                    {
                        if (this.fields[i][j].getColor() != step)
                        {
                            k_row = i;
                            k_col = j;
                            found = true;
                            break;
                        }
                    }
                }
            
                if (found)
                {
                    break;
                }
            }
            
            for (int i = -1; i <= 1; i++)
            {
                for (int j = -1; j <= 1; j++)
                {
                    if (this.fields[k_row][k_col].canMove(k_row, k_col, k_row + i, k_col + j))
                    {
                        if (this.fields[k_row + i][k_col + j] == null)
                        {
                            return false;
                        }
                    }
                }
            }
            
            return true;
        }
        
        return false;
    }
    
    // = Функция движения фигур =
    public boolean move_figure(int row, int col, int row1, int col1)
    {
      Figure figure = this.fields[row][col];
      
      // Если: (поле "от" не пустое) && (фигуру двигать можно) && (поле "в" пустое) && (цвет фигуры разрешен для хода)
      if (
              figure != null &&
              figure.canMove(row, col, row1, col1) &&
              this.fields[row1][col1] == null &&
              figure.getColor() == this.colorGame &&
              canMoveOnBoard(row, col, row1, col1))
      {
          this.fields[row1][col1] = figure;
          this.fields[row][col] = null;
          return true;
      }
      // Если: (атаковать можно) && (поле "в" не пустое) && (цвета фигур для атаки не совпадают)
      else if (
              figure.canAttack(row, col, row1, col1) &&
              this.fields[row1][col1] != null &&
              this.fields[row1][col1].getColor() != this.fields[row][col].getColor() &&
              canMoveOnBoard(row, col, row1, col1))
      {
          this.fields[row1][col1] = figure;
          this.fields[row][col] = null;

          switch (this.fields[row1][col1].getColor())
          {
              case 'w': this.takeWhite.add(this.fields[row1][col1].getColor() + this.fields[row1][col1].getName()); break;
              case 'b': this.takeBlack.add(this.fields[row1][col1].getColor() + this.fields[row1][col1].getName()); break;
          }
          
          return true;
      }
      
      return false;
    }
}
