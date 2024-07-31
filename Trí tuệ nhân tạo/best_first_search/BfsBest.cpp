#include<bits/stdc++.h>
using namespace std;

typedef pair<int, string> Diem;

void bfs(string root, int a, ofstream& output);
void printPath(string des, ofstream& output);

priority_queue<Diem, vector<Diem>, greater<Diem>> Q;
priority_queue<Diem, vector<Diem>, greater<Diem>> temp;
unordered_map<string, bool> visited;
map<Diem, vector<Diem>> graph;
map<string, string> revert;

string start, stop;

void BFS(string root, int a, ofstream& output) {
    Q.push(make_pair(a, root));
    visited[root] = true;
	output<<setw(20)<<"Phat trien tran thai"<<setw(20)<<"Trang thai ke"<<setw(40)<<"Danh Sach L"<<endl;
	output<<"=================================================================================="<<endl;
    while (!Q.empty()) {
        Diem x = Q.top();
        Q.pop();
        output << setw(10) << x.second << "-" << to_string(x.first) << setw(8) << " |";
        if (x.second == stop) {
            output << "\n\nFounded the Destination : " << stop << " <- ";
            printPath(stop, output);
			output << "\n";
            return ;
        }
        string str = "";
        for (auto i : graph[x]) {
            str += " " + i.second + "-" + to_string(i.first) + " ";
            if (visited.find(i.second) == visited.end()) {
                revert[i.second] = x.second;
                Q.push(i);
                visited[i.second] = true;
            }
        }
        output << str << setw(42 - str.size()) << " |";
        temp = Q;
        while (!temp.empty()) {
            output << temp.top().second << "-" << temp.top().first << " ";
            temp.pop();
        }
        output << "\n";
    }  
}

void printPath(string des, ofstream& output) {
    output << " " << revert[des];
    if (revert[des] == start) { 
		output << " ";
    } else { 
		output << " <- "; 
	}
    while (revert.find(revert[des]) != revert.end()) {
        printPath(revert[des], output);
        return;
    }
}
    
int main() {
    ifstream input("BestInput.txt");
    ofstream output("BestOutput.txt");
	int n; input >> n;
	for(int i = 0; i < n ; i++){
		int a, c; string b;
		input >> a >> b >> c;
		for(int j = 0; j < a; j++){
			string x; int d, e;
			input >> d >> x;
			graph[make_pair(c, b)].push_back(make_pair(d, x));
		}		
	}
	input >> start >> stop;	
	BFS(start, 20, output);
	input.close();
    output.close();
    return 0;
}
