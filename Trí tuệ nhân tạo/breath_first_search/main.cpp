#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <unordered_map>
#include <string>
using namespace std;

void BFS(unordered_map<string, vector<string>>& graph, const string& start, const string& destination, ofstream& output) {
    queue<string> q;
    unordered_map<string, bool> visited;
    unordered_map<string, string> parent;
    queue<string> tempL;  // Danh sách t?m
    q.push(start);
    visited[start] = true;

    output << "      Xet trang thai       Trang thai ke         Danh Sach L\n";
    output << "===============================================================\n";

    while (!q.empty()) {
        string current = q.front();
        q.pop();
        output << "           " << current << "          |";
        if (current == destination) {
            // Found the destination, reconstruct and print the path
            vector<string> path;
            while (!current.empty()) {
                path.push_back(current);
               
                current = parent[current];
            }

			output << "\n\nFounded the Destination :";
            // Write the path to output file
            for (int i = path.size() - 1; i >= 0; --i) {
                output << " " << path[i] << (i > 0 ? " -> " : " |\n");
            }

            // Write the list L to output file
            output << "                    |";
            while (!tempL.empty()) {
                output << " " << tempL.front();
                tempL.pop();
            }
            output << "\n";
            return;
        }
        for (const string& neighbor : graph[current]) {
            if (!visited[neighbor]) {
                q.push(neighbor);
                visited[neighbor] = true;
                parent[neighbor] = current;
            }
        }
        // Print the neighbors to console
        for (const string& neighbor : graph[current]) {
            output << " " << neighbor;
        }
        output <<"                  |   ";
		tempL = q; 
        while(!tempL.empty()){
				output << tempL.front().c_str() << " ";
				tempL.pop();
			}
        output << " \n";
    }

    // Write "Destination not found" to output file
    output << " Destination not found.\n";
}

int main() {
    ifstream input("input1.txt");
    ofstream output("output.txt");

    int n;
    input >> n;

    unordered_map<string, vector<string>> graph;

    for (int i = 0; i < n; ++i) {
        string node;
        int num_neighbors;
        input >> num_neighbors >> node;

        vector<string> neighbors(num_neighbors);
        for (int j = 0; j < num_neighbors; ++j) {
        	if()
        typeid(str) == typeid(std::string)
            input >> neighbors[j];

        }
        graph[node] = neighbors;
    }

    // Starting node and destination node
    string start, destination;
    input >> start >> destination;

    // Perform BFS
    BFS(graph, start, destination, output);

    input.close();
    output.close();

    return 0;
}

