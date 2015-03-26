#include <iostream>
#include <string>

using namespace std;


bool isSimpleCycle(unordered_set<pair<string,string>> edge_set, vector<string> P){
    // No vertex is visited twice
    // Except start vertex which is visited again at the end.
    // each vertex are connected by an edge

    for (vector<string>::iterator i = P.begin(); i != P.end(); ++i){
    }

    return false;
}

bool isHamiltonianCycle(unordered_set<pair<string,string>> edge_set, vector<string> P){
    isSimpleCycle(edge_set, P);
}

int main(){
    // T: Test Cases
    int T;

    // Graph Data Structure
    // N: # vertices
    // M: # edges
    int N, M;   

    // Hamiltonian Cycle
    // p: # of vertices in the cycle
    int p;


    cin >> T;

    for (int i=0; i < T; ++i){

        cin >> N >> M; // vertex & edge

        unordered_set<pair<string, string>> edge_set;

        for (int j = 0; j < M; ++j){ // for each edges
            string u, v;

            cin >> u >> v;

            // Add u & v to data structure
            pair<string, string> edge {u, v};
            edge_set.insert(edge);
        }

        cin >> p;
        vector<string> P;
        for (int i = 0; i < p; ++p){
            string vert;
            cin >> vert;

            P.push_back(vert);
        }

        if (isHamiltonianCycle(edge_set, P)) {
            cout << "YES" << endl;
        } else {
            cout << "NO" << endl;
        }
    }
    return 0;
}