#include<bits/stdc++.h>
using namespace std;

class Solution{
private:
    bool dfs(int currNode, vector<vector<int>> &adj, vector<bool> &visited, vector<bool> &recurStack){
        visited[currNode]=true;
        recurStack[currNode]=true;

        for(int &node: adj[currNode]){
            if(!visited[node] && dfs(node, adj, visited, recurStack)){
                return true;
            }   
            if(recurStack[node]){
                return true;
            }
        }
        recurStack[currNode]=false;

        return false;
    }
public:
    bool hasCircularDependency(int n, vector<vector<int>> &edges){
        vector<vector<int>> adj(n);
        for(auto &e:edges){
            adj[e[0]].push_back(e[1]);
        }
        vector<bool> visited(n, false);
        vector<bool> recurStack(n, false);
        
        for(int node=0;node<n;node++){
            if(!visited[node] && dfs(node, adj, visited, recurStack)){
                return true;
            }
        }
        return false;
    }
};

int main(){
    // system("cls");
    Solution s;
    int n = 4;
    vector<vector<int>> edges = {{0, 1}, {1, 2}, {2, 3}};
    bool result = s.hasCircularDependency(n, edges);
    cout<<result<<endl;

    n = 4;
    edges = {{0, 1}, {1, 2}, {2, 0}};
    result = s.hasCircularDependency(n, edges);
    cout<<result<<endl;
    return 0;
}