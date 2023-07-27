#include <bits/stdc++.h>
using namespace std;

void Todisplay(string d[3][3])
{

    for (int i = 0; i < 3; i++)
    {

        for (int j = 0; j < 3; j++)
        {

            /* code */
            // cout<<" "<<d[i][j]<<" "<<"|";
            if (j == 2)
            {
                cout << " " << d[i][j] << " ";
            }
            else
            {
                cout << " " << d[i][j] << " "
                     << "|";
            }
        }
        if (i == 2)
        {
            cout << endl;
            break;
        }

        cout << endl
             << "----------";
        cout << endl;

        /* code */
    }
}

/// to check win or lose
bool win_or_lose(string d[3][3])
{
    bool check = false;

    // check horizontal
    for (int i = 0; i < 3; i++)
    {

        if (d[i][0] == d[i][1] && d[i][1] == d[i][2])
        {
            cout << endl
                 << "checked and winning" << endl;
            check = true;
        }
    }

    // CHECK vertical
    for (int i = 0; i < 3; i++)
    {

        if (d[0][i] == d[1][i] && d[0][i] == d[2][i])
        {
            cout << endl
                 << "checked and winning" << endl;
            check = true;
        }
    }

    // check diagonal
    if (d[0][0] == d[1][1] && d[0][0] == d[2][2])
    {
        check = true;
    }

    if (d[0][2] == d[1][1] && d[0][2] == d[2][0])
    {
        check = true;
    }

    return check;
}
///

void display_player(string d[3][3])
{
    int kk = 8;
    int player_turn = 1;
    while (kk--)
    {
        /* code */

        int no;
        char op;
        kk % 2 == 0 ? player_turn = 2 : player_turn = 1;
        if (kk % 2)
        {
            op = 'x';
        }
        else
        {
            op = 'o';
        }

        // to user
        cout << endl
             << "TURN:-------PLAYER NO " << player_turn << "-------" << endl;

        cout << endl
             << "Enter the number where " << op << " to be placed :";
        char get;
        cin >> get;
        string compare;
        compare = get;

        string select;

        select = op;

        // to put in matrix

        for (size_t i = 0; i < 3; i++)
        {
            /* code */
            for (int j = 0; j < 3; j++)
            {
                if (compare == d[i][j])
                {
                    d[i][j] = select;
                }
            }
        }
        // check----------------win or los --------
        if (kk == 0)
        {
            cout << endl
                 << "------------- DRAW----------------" << endl;
            Todisplay(d);
            cout << endl
                 << " --NO ONE WIN THE GAME--" << endl;
            break;
        }
        bool check = win_or_lose(d);
        if (check == true)
        {
            cout << endl
                 << " THE WINNER OF THE GAME IS PLAYER  :" << player_turn << endl;
            Todisplay(d);
            cout << endl
                 << "/-/-/-/-/-/-/-/-/-/-/ YOU ROCKED PLAYER  " << player_turn << " -/-/-/-/-/-/-/-/-/-/" << endl;
            cout << endl
                 << "----------------The end------------------------" << endl;
            break;
        }
        ///

        Todisplay(d);
    }
}

void player_vs_player(string d[3][3])
{

    cout << endl;
    cout << " player 1 : x" << endl
         << " player 2 : o" << endl
         << endl;

    Todisplay(d);

    // to display in loop player

    display_player(d);
}
int main()
{

// intilize--
again_loop:

    int no = 1;
    string d[3][3];
    for (int i = 0; i < 3; i++)
    {
        /* code */
        for (int j = 0; j < 3; j++)
        {
            d[i][j] = to_string(no++);
        }
    }
    int normal;
    cout << " 0: comp vs player \n 1: player vs player  " << endl
         << "Enter Any One Operation : ";
    cin >> normal;
    if (normal)
    {
        player_vs_player(d);
    }
    else
    {

        // comp vs player here
    }

    // to play again
    string again;

    cout << endl
         << endl
         << "Want to Play Again " << endl
         << "Enter yes or no  : ";
    getline(cin, again);
    if (again == "yes")
    {
        goto again_loop;
    }
    // loops end

    return 0;
}
