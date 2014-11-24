Sudoku_Dancing-link
===================

Solve Sudoku Puzzle dancing link

Abstract:
The objective of this report is to present detailed algorithm and implementation of sudoku solver..The
algorithm solves the sudoku puzzle entered by user of any difficulty level(easy,medium,hard,very
hard).Algorithm has been implemented using JAVA ,user can enter the puzzle input using JAVA
console ,algorithm process the input and produce output on console itself.

Algorithm:-
Algorithm focuses on creating dancing link ,reduce the puzzle in to exact cover form and solve it using
algorithm X in a efficient way.To make it efficient,first we implemented rule1 and rule2 ,firstly
algorithm try to solve sudoku puzzle using rule1 and rule2 recursively and filled some cells which helps
us to reduce no of rows when we convert it in to exact cover form.Applying algorithm X will give us a
solution.
For easy and medium puzzles ,using only rule1 and rule2 algorithm solved puzzle completely in less
than 0.005 seconds.
For hard and very hard puzzles ,algorithm first fill some cells using rule1 and rule2 which help us to
reduce no of rows when we convert it in to exact cover and then apply algorithm X to solve it.Time
taken by the algorithm varies depends on the level of input.
I have tried some inputs and algorithm is working for 81*81 , i haven't try the input larger than that.
For given n ,we have n^2 rows and n^2 columns ,thus we have n^4 cells. For each cell which is
empty ,we have an array of n^2 which keeps track of number of possible elements.

Algorithm is divided in to following modules:-
1.)Pencil-Marks/possible elements of each cell.
2.)Single Candidate/Rule1
3.)Hidden Single/Rule2
4.)Dancing Link/Exact Cover/Algorithm X for backtracking

Please see the code to get the feel of algorithm.


