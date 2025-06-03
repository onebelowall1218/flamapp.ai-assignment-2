#include<bits/stdc++.h>

using namespace std;

class Solution{

private:
    void backtrack(int row, int n, vector<string> &board, vector<bool> &cols, vector<bool> &diag1, vector<bool> &diag2, vector<vector<string>> &result){
        if(row==n){
            result.push_back(board);
            return;
        }

        for(int col=0;col<n;col++){
            if(cols[col] || diag1[row-col+n-1] || diag2[row+col]){
                continue;
            }

            board[row][col]='Q';
            cols[col]=true;
            diag1[row-col+n-1]=true;
            diag2[row+col]=true;
            
            backtrack(row+1, n, board, cols, diag1, diag2, result);
            
            board[row][col]='.';
            cols[col]=false;
            diag1[row-col+n-1]=false;
            diag2[row+col]=false;
            
        }
    }

public:
    vector<vector<string>> nQueens(int n){
        vector<vector<string>> result;
        vector<string> board(n, string(n, '.'));
        vector<bool> cols(n, false);
        vector<bool> diag1(2*n-1, false);
        vector<bool> diag2(2*n-1, false);
        backtrack(0, n, board, cols, diag1, diag2, result);
        return result;
    }

    void displayBoards(vector<vector<string>> &boards){
        for(auto &board: boards){
            for(auto row: board){
                cout<<row<<endl;
            }
            cout<<endl;
        }
    }
};

int main(){
    // system("cls");
    Solution s;
    int n;
    cin>>n;
    vector<vector<string>> result = s.nQueens(n);
    s.displayBoards(result);
    return 0;
}