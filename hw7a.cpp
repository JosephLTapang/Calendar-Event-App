/*****************************************************************
//
//  NAME: Christian Takemoto
//
//  HOMEWORK:  7
//
//  CLASS:   ICS 212
//
//  INSTRUCTOR:     Ravi Narayan
//
//  DATE:      July 11, 2014           
//
//  FILE:  	 hw7a.cpp  
//
//  DESCRIPTION:     This file contains functions for getting the user's
//                   input and then printing temperatures from 0 to the
//                   closest multiple of 5 greater than or equal to that
//                   number. Both degrees fahrenheit and celsius are displayed.
//                   
//
//****************************************************************/

#include<iostream>
using namespace std;
#include<iomanip>

int getUserInput(int *temp);
int rounder(int *temp);
int print(int maxTemp);
void convertFtoC(int fahrenheit, float& celsius);

/*****************************************************************
//
//  Function name:  main
//
//  DESCRIPTION:    Runs the program, getting user input, and then printing an increasing
//                  list of temperatures in fahrenheit and celsius.
//
//  Parameters:     NONE
//
//  Return values:  0: success
//
//****************************************************************/
int main()
{
   int temp;
   int problem = 0;
   if(getUserInput(&temp) == 0)/*if getUserInput goes smoothly*/
   {
      problem = rounder(&temp);
      problem = print(temp);
   }
   return 0;
}

/*****************************************************************
//
//  Function name:  getUserInput
//
//  DESCRIPTION:    Gets the user's positive integer input. Has the user reenter the 
//                  number if it is negative or more than or less than 1 argument.
//                  Also prints error messages explaining to the user what went wrong.
//
//  Parameters:     pointer to the temp variable passed from main which stores the user's input
//
//  Return values:  0: success
//
//****************************************************************/
int getUserInput(int *temp)
{
   int rtn = 0;/*return 0. if user input is incorrect, change to 1*/
   int c;
   do
   {
      c = '\0';
      cout << "Please enter a positive maximum temperature in Fahrenheit (F):\n";
      
      cin >> *temp;/*Should be 1 argument*/
      if(cin)/*if input good*/
      {
         if(*temp > 0)
         {
            rtn = 0;/*Input good*/
         }
         else
         {
            rtn = 1;
            cout << "You entered a negative number or 0. Please enter a positive number.\n";
         }
      }
      else
      {
         rtn = 1;
         cout << "Your input was either not a number or did not have just 1 argument. Please enter only 1 positive number and try again.\n";
         while(c != '\n')/*clear buffer*/
         {
            cin >> c;
         }
      }
   }while(rtn == 1);/*While still problem*/
   
   return 0;
}

/*****************************************************************
//
//  Function name:  rounder
//
//  DESCRIPTION:    Rounds the user's inputted number to the closest
//                  multiple of 5 equal to or above it, then changes
//                  the value of the user's input to that rounded number.
//
//  Parameters:     pointer to the main functions temp variable which holds user's input temperature
//
//  Return values:  0: success
//
//****************************************************************/
int rounder(int *temp)
{
   int a = *temp;
   if((a % 5) != 0)
   {
      *temp = a+(5-(a%5));
   }
   return 0;
}

/*****************************************************************
//
//  Function name:  print
//
//  DESCRIPTION:    Prints all temperatures from 0 degrees Fahrenheit
//                  to the new rounded temperature value in multiples of
//                  5, along with the equivalent celsius values. 2nd decimal
//                  place for celsius degrees
//
//  Parameters:     maximum temp for this function to print.
//
//  Return values:  0: success
//
//****************************************************************/
int print(int maxTemp)
{
   float tempC;
   int i;
   cout << "Temperature in F    Temperature in C\n";
   for(i = 0; i <= maxTemp; i = i+5)
   {
      convertFtoC(i,tempC);
      cout << i << "                     " << setprecision(4) << tempC << "\n";
   }
   return 0;
}

/*****************************************************************
//
//  Function name:  convertFtoC
//
//  DESCRIPTION:    Converts fahrenheit degrees to celsius degrees
//
//  Parameters:     degrees fahrenheit to convert to degrees celsius
//                  reference to a celsius variable to fill
//
//  Return values:  NONE
//
//****************************************************************/
void convertFtoC(int fahrenheit, float& celsius)
{
   celsius = (fahrenheit-32.0f)*(5.0f/9.0f);
}
