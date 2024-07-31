//byDuc
#include<bits/stdc++.h>
using namespace std;
// debai :
void Phangiai(map<int,vector<string>& G, string H, ofstream& output) {
	
}

int main() {
	ifstream input("inputBacBo.txt");
    ofstream output("outputBacBo.txt");
    int n;
    input >> n;
    map<int, vector<string>> G;
    for(int i = 0; i <  n; i ++) {
    	int x;
    	input >> x;
    	cout<<x;
    	vector<string> cautuyen(x);
    	for(int j=0;j<x;j++){
    		
    		input>>cautuyen[j];
    		
		}
		G[x]=cautuyen;
	}
	string H;
	input>>H;
	Phangiai(G,H,output)
//	cout << "Danh sách các t?: " << endl;
//    for (const auto& pair : G) {
//        cout << "Câu th? " << pair.first + 1 << ": ";
//        for (const auto& word : pair.second) {
//            cout << word << " ";
//        }
//        cout << endl;
//    }
//    cout << "Chu?i H: " << H << endl;

    return 0;
}

