#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <cmath>
#include <map>
#include <algorithm>
#include <bits/stdc++.h> 
using namespace std;

typedef pair <string,int> p1;

// node luu tru do thi
struct Node {
    p1 id_f;    // ID c?a n�t v� chi ph� t�nh t? di?m b?t d?u
    int g_cost; // Chi ph� t? di?m b?t d?u d?n n�t hi?n t?i
    int h_cost; // Chi ph� u?c lu?ng t? n�t hi?n t?i d?n di?m k?t th�c
    int k_cost; // Chi ph� th?c c?a c?nh t? n�t hi?n t?i d?n n�t k? ti?p

    // H�m kh?i t?o c?a c?u tr�c Node
    Node(pair <string,int> _id_f, int _g_cost, int _h_cost,int _k_cost) : id_f(_id_f), g_cost(_g_cost), h_cost(_h_cost), k_cost(_k_cost) {}
};

// C?u tr�c d? so s�nh hai d?i tu?ng Node
struct CompareNode {
    bool operator()(const Node& a, const Node& b) {
        return a.id_f.second > b.id_f.second; // So s�nh theo chi ph� t?ng (g_cost + h_cost)
    }
};

queue<string> Ind; // H�ng d?i d? luu tr? th�ng tin in ra cho m?i bu?c trong qu� tr�nh t�m ki?m

string start, stop; // Node b?t d?u v� node k?t th�c

// Class AStar th?c hi?n thu?t to�n t�m du?ng di ng?n nh?t A*
class AStar {
private:
    map<string, vector<pair<string,pair<int, int>>>> graph; // �? th? luu du?i d?ng danh s�ch k?
    map<string, int> hx; // B?ng u?c lu?ng heuristics c?a m?i node
    map<string, string> parent; // B?ng luu tr? cha c?a m?i node trong qu� tr�nh t�m ki?m

public:
    // H�m kh?i t?o c?a l?p AStar, d?c d? li?u t? t?p v� kh?i t?o c�c bi?n th�nh vi�n
    AStar(const string& filename) {
        ifstream file(filename); // M? t?p d?u v�o
        int num_nodes;
        file >> num_nodes >> start >> stop; // �?c s? lu?ng node, node b?t d?u v� node k?t th�c t? t?p
        
        // �?c heuristics c?a m?i node v� luu v�o b?ng hx
        for (int i = 0; i < num_nodes; ++i) {
            int h;
            string u;
            file >> u >> h;
            hx[u] = h;
        }

        // �?c c�c c?nh c?a m?i node v� luu v�o bi?n graph du?i d?ng danh s�ch k?
        for (int i = 0; i < num_nodes; ++i) {
            string u;
            int num_edges;
            file >> num_edges >> u;
            for (int j = 0; j < num_edges; ++j) {
                int k;
                string v;
                file >> v >> k;
                auto h = hx.find(v);
                if (h == hx.end()) { // Ki?m tra t�nh h?p l? c?a d? li?u
                    cout << "Loi du lieu:"; // N?u d? li?u kh�ng h?p l?, xu?t th�ng b�o l?i v� k?t th�c chuong tr�nh
                    return;
                }
                graph[u].push_back({v,{k,h->second}});
            }
        }
        file.close(); // ��ng t?p sau khi d?c xong
    }

    // H�m t�m du?ng di t? node start d?n node goal v� ghi k?t qu? v�o t?p output
    vector<p1> search(string start, string goal, ofstream& output) {
        output << "|==========================================================================================================================================================|" << endl;
        output << setw(10) << "| Phat trien tran thai  | " << setw(34) << "            Trang thai ke :     k   ;   h    ;     g    ;    f          |" << setw(48) << "Danh Sach L\t\t   |" << endl;
        output << "|==========================================================================================================================================================|" << endl;

        // Kh?i t?o h�ng d?i uu ti�n d? l?a ch?n node ti?p theo cho vi?c m? r?ng
        priority_queue<Node, vector<Node>, CompareNode> pq;

        // Kh?i t?o b?ng chi ph� t? di?m b?t d?u d?n m?i node
        map<string, int> g_cost;

        // Kh?i t?o b?ng luu tr? cha c?a m?i node trong qu� tr�nh t�m ki?m
        map<p1, p1> parent;

        // �ua node b?t d?u v�o h�ng d?i uu ti�n v?i c�c th�ng s? tuong ?ng
        pq.push(Node({start, 0}, 0, hx.find(start)->second, 0));

        // B?t d?u v�ng l?p ch�nh c?a thu?t to�n A*
        while (!pq.empty()) {
            Node current = pq.top(); // L?y ra node c� chi ph� t?ng nh? nh?t t? h�ng d?i uu ti�n
            pq.pop(); // Lo?i b? node v?a l?y ra kh?i h�ng d?i

            string strIn = ""; // Chu?i d? luu th�ng tin in ra
            strIn = current.id_f.first + " - " + to_string(current.id_f.second);
            output << "|\t" << left << strIn << "\t\t|";  // In th�ng tin v? node hi?n t?i

            // Ki?m tra xem node hi?n t?i c� ph?i l� node k?t th�c hay kh�ng
            if (current.id_f.first == goal) {
                output << "\t\t\t\t\t\t\t\t\t  " << left << setw(40) << "|" << "\t\t   |";

                // N?u l� node k?t th�c, tr? v? du?ng di t? node b?t d?u d?n node k?t th�c
                vector<p1> path;
                p1 curr = current.id_f;
                while (curr.first != start) {
                    path.push_back(curr);
                    curr = parent[curr]; // Ti kiem tra lai
                }
                path.push_back({start, 0});
                reverse(path.begin(), path.end());
                return path;
            }

            // Duy?t qua c�c node k? c?a node hi?n t?i d? m? r?ng
            for (auto& neighbor : graph[current.id_f.first]) {
                string neighbor_id = neighbor.first;
                int k_cost = neighbor.second.first; // Chi ph� th?c c?a c?nh t? node hi?n t?i d?n node k?
                int h_cost = neighbor.second.second; // Chi ph� u?c lu?ng t? node k? d?n node k?t th�c
                int new_g_cost = current.g_cost + k_cost; // Chi ph� m?i t? di?m b?t d?u d?n node k?
                int f_cost = new_g_cost + h_cost; // Chi ph� t?ng (g_cost + h_cost)

                // T?o m?t node m?i v?i c�c th�ng s? d� t�nh to�n
                Node t = Node({neighbor_id, f_cost}, new_g_cost, h_cost, k_cost);

                // �ua node m?i v�o h�ng d?i uu ti�n
                pq.push(t);

                // C?p nh?t th�ng tin v? cha c?a node k? trong qu� tr�nh t�m ki?m
                parent[{neighbor_id, f_cost}] = current.id_f;

                // Luu th�ng tin c?a node k? v�o h�ng d?i d? in ra sau n�y
                strIn = neighbor_id + " - " + to_string(k_cost) + " - " + to_string(h_cost) + " - " + to_string(new_g_cost) + " - " + to_string(f_cost) + " ; ";
                Ind.push(strIn);
            }

            // In th�ng tin v? node k? v� c�c th�ng s? c?a n�
            output << "\t\t\t" << left << setw(50) << Ind.front()  << "|\t\t\t";
            Ind.pop(); // Lo?i b? node v?a in ra kh?i h�ng d?i

            // In ra danh s�ch c�c node trong h�ng d?i uu ti�n
            output << setw(35) << strIn << "|\n";
            while (!Ind.empty()) {
                strIn = Ind.front();
                Ind.pop();
                output << "|\t\t\t|\t\t\t" << left << setw(50) << strIn << "|" << "\t\t\t\t\t\t\t   |";
                output << " \n";
            }
            output << "|----------------------------------------------------------------------------------------------------------------------------------------------------------|" << endl;
        }

        return vector<p1>(); // Tr? v? vector r?ng n?u kh�ng t�m th?y du?ng di
    }
};

// H�m main
int main() {
    ofstream output("outputASao.txt"); // M? t?p output d? ghi k?t qu?
    AStar astar("graph.txt"); // T?o m?t d?i tu?ng AStar v� d?c d? li?u t? t?p "graph.txt"
    vector<p1> path = astar.search(start, stop, output); // T�m du?ng di t? node start d?n node stop v� ghi k?t qu? v�o t?p output

    output << "\n"; // Xu?ng d�ng

    // Xu?t th�ng b�o n?u kh�ng t�m th?y du?ng di ho?c xu?t du?ng di n?u t�m th?y
    if (path.empty()) {
        output << "No path found!" << endl;
    } else {
        output << "|----------------------------------------------------------------------------------------------------------------------------------------------------------|" << endl;
        output << "Path found: ";
        for (p1 node : path) {
            if (node.first == stop) output <<  node.first << " " << endl << "Length = " << node.second;
            else output << node.first << " -> ";
        }
        output << endl;
    }

    return 0;
}

