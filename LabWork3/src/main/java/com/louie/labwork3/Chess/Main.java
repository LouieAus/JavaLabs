package com.louie.labwork3.Chess;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        // Создаем доску и инициализируем
        Board board = new Board();
        board.init();
        board.setColorGame('w');

        System.out.println();

        boolean isGame = true;
        Scanner in = new Scanner(System.in);
        
        boolean check = false;

        // Главный цикл игры
        while (isGame)
        {
            // Отрисовка доски
            board.print_board();
            System.out.println();

            // Отрисовка информации
            System.out.println("Управление:");
            System.out.println("----row ol row1 col1: Ход фигуры из клетки (row, col) в (row1, col1)");
            System.out.println("Взятые Белые: " + board.getTakeWhite().toString());
            System.out.println("Взятые Черные: " + board.getTakeBlack().toString());
            
            // Отрисовка хода
            switch (board.getColorGame())
            {
                case 'w': System.out.println("Ход белых"); break;
                case 'b': System.out.println("Ход черных"); break;
            }
            
            int row, col, row1, col1;
            if (check)
            {
                System.out.print("ШАХ! Введите ход: ");
                String inputLine = in.nextLine();
                
                int[] king_coords = board.getKing();
                row = king_coords[0];
                col = king_coords[1];
                
                String[] coords = inputLine.split(" ");
                row1 = Integer.parseInt(coords[0]);
                col1 = Integer.parseInt(coords[1]);
                
                while (!board.move_figure(row, col, row1, col1))
                {
                    // Снова читаем ход
                    System.out.println("Ошибка! Повторите ход короля!");
                    System.out.print("Введите ход: ");
                    inputLine = in.nextLine();
                    coords = inputLine.split(" ");
                    row1 = Integer.parseInt(coords[0]);
                    col1 = Integer.parseInt(coords[1]);
                }
                
                check = false;
            }
            else
            {
                // Чтение хода
                System.out.print("Введите ход: ");
                String inputLine = in.nextLine();
                String[] coords = inputLine.split(" ");
                row = Integer.parseInt(coords[0]);
                col = Integer.parseInt(coords[1]);
                row1 = Integer.parseInt(coords[2]);
                col1 = Integer.parseInt(coords[3]);

                // Передвижение фигуры
                // Если: (нельзя двигать)
                while (!board.move_figure(row, col, row1, col1))
                {
                    // Снова читаем ход
                    System.out.println("Ошибка! Повторите ход фигуры!");
                    System.out.print("Введите ход: ");
                    inputLine = in.nextLine();
                    coords = inputLine.split(" ");
                    row = Integer.parseInt(coords[0]);
                    col = Integer.parseInt(coords[1]);
                    row1 = Integer.parseInt(coords[2]);
                    col1 = Integer.parseInt(coords[3]);
                }
            }
            
            if (board.isMate(row1, col1))
            {
                System.out.print("Игра окончена!");
                break;
            }
            
            if (board.isCheck(row1, col1))
            {
                check = true;
            }

            // Смена хода
            switch (board.getColorGame())
            {
                case 'w': board.setColorGame('b');break;
                case 'b': board.setColorGame('w');break;
            }
        }
    }
}
