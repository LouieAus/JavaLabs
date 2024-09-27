/*
    Лабораторная работа №1
    Выполнил студент 3 курса ПМ
    Львов Илья
*/

package com.louie.labwork1;

import java.util.Scanner;

public class LabWork1
{
    public static void main(String[] args)
    {
        System.out.println(Exrc1.CalculateSyracuse());
        
        System.out.println(Exrc2.GetRawSumm());
        
        System.out.println(Exrc3.GetMinMovements());
        
        Exrc4.PrintTunnelHeight();
        
        System.out.println(Exrc5.IsTwiceEven());
    }
}

// Задание 1: Сиракузская последовательность
class Exrc1
{
    static int CalculateSyracuse()
    {
        Scanner input = new Scanner(System.in);
        int value = input.nextInt();
        
        int count = 0;
        while (value != 1)
        {
            value = (value % 2 == 0) ? (value / 2) : (3 * value + 1);
            count++;
        }
        return count;
    }
}

// Задание 2: Сумма ряда
class Exrc2
{
    static int GetRawSumm()
    {
        Scanner input = new Scanner(System.in);
        
        int n = input.nextInt();
        int summ = 0;
        int mult = 1;
        for (int i = 0; i != n; i++)
        {
            int value = input.nextInt();
            summ += mult * value;
            mult *= -1;
        }
        
        return summ;
    }
}

// Задание 3: Ищем клад
class Exrc3
{
    static class XY
    {
        int x, y;
    }
    
    static int GetMinMovements()
    {
        Scanner input = new Scanner(System.in);
        
        XY current_coord = new XY();
        current_coord.x = 0;
        current_coord.y = 0;
        
        XY coord = new XY();
        coord.x = input.nextInt();
        coord.y = input.nextInt();
        
        int steps = 0;
        while (true)
        {
            String direct = input.next();
            if (direct.equals("стоп"))
                break;
                        
            int value = input.nextInt();
            if (direct.equals("север") && current_coord.y != coord.y)
            {
                current_coord.y += value;
                steps++;
            }
            else if (direct.equals("юг") && current_coord.y != coord.y)
            {
                current_coord.y -= value;
                steps++;
            }
            else if (direct.equals("восток") && current_coord.x != coord.x)
            {
                current_coord.x += value;
                steps++;
            }
            else if (direct.equals("запад") && current_coord.x != coord.x)
            {
                current_coord.x -= value;
                steps++;
            }
        }
        
        return steps;
    }
}

// Задача 4: Логистический максимин
class Exrc4
{
    static void PrintTunnelHeight()
    {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        
        int tunnel = 1;
        int height = 0;
        
        for (int i = 1; i != n + 1; i++)
        {
            int t = input.nextInt();
            
            int minTunnelHeight = -1;
            for (int j = 0; j != t; j++)
            {
                int h = input.nextInt();
                if (h < minTunnelHeight || minTunnelHeight == -1)
                    minTunnelHeight = h;
            }
            
            if (minTunnelHeight > height)
            {
                height = minTunnelHeight;
                tunnel = i;
            }
        }
        
        System.out.println(tunnel + " " + height);
    }
}

// Задача 5: Дважды четное число
class Exrc5
{
    static boolean IsTwiceEven()
    {
        Scanner input = new Scanner(System.in);
        int number = input.nextInt();
        
        int a = 0;
        int b = 1;        
        while (number != 0)
        {
            int digit = number % 10;
            a += digit;
            b *= digit;
            number /= 10;
        }
        
        return (a % 2 == 0 && b % 2 == 0) ? true : false;
    }
}