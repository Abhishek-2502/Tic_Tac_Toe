package com.example.tic_tac_toe

class SolverUtils {

    fun test(){
        val grid = arrayOf(
            arrayOf(1, 0, 1),
            arrayOf(1, 0, 1),
            arrayOf(-1, 1, 0)
        )
        printgrid(grid)
        var winner : String = solver(grid)
        println(winner)
    }

    fun printgrid(grid: Array<Array<Int>>){
        for(row in 0..2){
            for(col in 0..2){
                print("${grid[row][col]} ")
            }
            println()
        }
    }

    fun resetgrid(grid: Array<Array<Int>>){
        for(row in 0..2){
            for(col in 0..2){
                grid[row][col]=-1
            }
        }
    }

    fun solver(grid: Array<Array<Int>>) : String{
        /*
     * 00 01 02
     * 10 11 12
     * 20 21 22
     */
        for (row in 0..2) {
            if (grid[row][0] != -1) {
                if (grid[row][0] == grid[row][1] && grid[row][0] == grid[row][2]) {
                    if (grid[row][0] == 0) {
                        return "PC"
                    } else {
                        return "Human"
                    }
                }
            }
        }

        /*
         * 00
         * 10
         * 20
         *
         * 01
         * 11
         * 21
         *
         * 02
         * 12
         * 22
         */
        for (col in 0..2) {
            if (grid[0][col] != -1) {
                if (grid[0][col] == grid[1][col] && grid[0][col] == grid[2][col]) {
                    if (grid[0][col] == 0) {
                        return "PC"
                    } else {
                        return "Human"
                    }
                }
            }
        }

        /*
         *
         * Diagonal
         * 00
         * 11
         * 22
         */
        if(grid[0][0]!=-1){
            if(grid[0][0]==grid[1][1] && grid[0][0]==grid[2][2]){
                if(grid[0][0]==0){
                    return "PC"
                }else{
                    return "Human"
                }
            }
        }

        /*
         * Diagonal
         * 02
         * 11
         * 20
         */
        if(grid[0][2]!=-1){
            if(grid[0][2]==grid[1][1] && grid[0][2]==grid[2][0]){
                if(grid[0][2]==0){
                    return "PC"
                }else{
                    return "Human"
                }
            }
        }

        //DRAW
        var flag : Int = 0
        for(row in 0..2){
            for(col in 0..2){
                if(grid[row][col]==-1){
                    flag=1;
                    break
                }
            }
        }
        if(flag==0){
            return "Draw"
        }

        return "Continue Game"
    }

    fun mediumalgo(grid: Array<Array<Int>>) : Array<Int>{
        var location = arrayOf(-1,-1)
        var count1 : Int = 0
        var countminus1 : Int = 0
        // Horizontal
        for (row in 0..2) {
            for(col in 0..2){
                if (grid[row][col] == 1){
                    count1++
                }
                else if(grid[row][col] == -1){
                    location[0]=row
                    location[1]=col
                    countminus1++
                }
                if(count1==2 && countminus1==1){
                    println("Med Found horizontal")
                    return location
                }
            }
            count1=0
            countminus1=0
        }

        // Vertical
        for (col in 0..2) {
            for(row in 0..2){
                if (grid[row][col] == 1){
                    count1++
                }
                else if(grid[row][col] == -1){
                    location[0]=row
                    location[1]=col
                    countminus1++
                }
                if(count1==2 && countminus1==1){
                    println("Med Found vertical")
                    return location
                }
            }
            count1=0
            countminus1=0
        }

        // Diagonal1
        for(i in 0..2){
            if (grid[i][i ] == 1){
                count1++
            }
            else if(grid[i][i] == -1){
                location[0]=i
                location[1]=i
                countminus1++
            }
            if(count1==2 && countminus1==1){
                println("Med Found d1")
                return location
            }
        }

        count1=0
        countminus1=0

        // Digonal2
        if (grid[0][2 ] == 1){
            count1++
        }
        else if(grid[0][2] == -1){
            location[0]=0
            location[1]=2
            countminus1++
        }
        if (grid[1][1 ] == 1){
            count1++
        }
        else if(grid[1][1] == -1){
            location[0]=1
            location[1]=1
            countminus1++
        }
        if (grid[2][0] == 1){
            count1++
        }
        else if(grid[2][0] == -1){
            location[0]=2
            location[1]=0
            countminus1++
        }
        if(count1==2 && countminus1==1){
            println("Med Found d2")
            return location
        }

        //Not found
        location[0]=-1
        location[1]=-1
        return location
    }

    fun hardalgo(grid: Array<Array<Int>>) : Array<Int>{
        var location = arrayOf(-1,-1)
        var count0 : Int = 0
        var countminus1 : Int = 0
        // Horizontal
        for (row in 0..2) {
            for(col in 0..2){
                if (grid[row][col] == 0){
                    count0++
                }
                else if(grid[row][col] == -1){
                    location[0]=row
                    location[1]=col
                    countminus1++
                }
                if(count0==2 && countminus1==1){
                    println("Hard Found horizontal")
                    return location
                }
            }
            count0=0
            countminus1=0
        }

        // Vertical
        for (col in 0..2) {
            for(row in 0..2){
                if (grid[row][col] == 0){
                    count0++
                }
                else if(grid[row][col] == -1){
                    location[0]=row
                    location[1]=col
                    countminus1++
                }
                if(count0==2 && countminus1==1){
                    println("Hard Found vertical")
                    return location
                }
            }
            count0=0
            countminus1=0
        }

        // Diagonal1
        for(i in 0..2){
            if (grid[i][i ] == 0){
                count0++
            }
            else if(grid[i][i] == -1){
                location[0]=i
                location[1]=i
                countminus1++
            }
            if(count0==2 && countminus1==1){
                println("Hard Found d1")
                return location
            }
        }

        count0=0
        countminus1=0

        // Digonal2
        if (grid[0][2 ] == 0){
            count0++
        }
        else if(grid[0][2] == -1){
            location[0]=0
            location[1]=2
            countminus1++
        }
        if (grid[1][1 ] == 0){
            count0++
        }
        else if(grid[1][1] == -1){
            location[0]=1
            location[1]=1
            countminus1++
        }
        if (grid[2][0] == 0){
            count0++
        }
        else if(grid[2][0] == -1){
            location[0]=2
            location[1]=0
            countminus1++
        }
        if(count0==2 && countminus1==1){
            println("Hard Found d2")
            return location
        }

        //Not found
        location[0]=-1
        location[1]=-1

        return location
    }
}