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
    p1 id_f;    // ID c?a nút và chi phí tính t? di?m b?t d?u
    int g_cost; // Chi phí t? di?m b?t d?u d?n nút hi?n t?i
    int h_cost; // Chi phí u?c lu?ng t? nút hi?n t?i d?n di?m k?t thúc
    int k_cost; // Chi phí th?c c?a c?nh t? nút hi?n t?i d?n nút k? ti?p

    // Hàm kh?i t?o c?a c?u trúc Node
    Node(pair <string,int> _id_f, int _g_cost, int _h_cost,int _k_cost) : id_f(_id_f), g_cost(_g_cost), h_cost(_h_cost), k_cost(_k_cost) {}
};

// C?u trúc d? so sánh hai d?i tu?ng Node
struct CompareNode {
    bool operator()(const Node& a, const Node& b) {
        return a.id_f.second > b.id_f.second; // So sánh theo chi phí t?ng (g_cost + h_cost)
    }
};

queue<string> Ind; // Hàng d?i d? luu tr? thông tin in ra cho m?i bu?c trong quá trình tìm ki?m

string start, stop; // Node b?t d?u và node k?t thúc

// Class AStar th?c hi?n thu?t toán tìm du?ng di ng?n nh?t A*
class AStar {
private:
    map<string, vector<pair<string,pair<int, int>>>> graph; // Ð? th? luu du?i d?ng danh sách k?
    map<string, int> hx; // B?ng u?c lu?ng heuristics c?a m?i node
    map<string, string> parent; // B?ng luu tr? cha c?a m?i node trong quá trình tìm ki?m

public:
    // Hàm kh?i t?o c?a l?p AStar, d?c d? li?u t? t?p và kh?i t?o các bi?n thành viên
    AStar(const string& filename) {
        ifstream file(filename); // M? t?p d?u vào
        int num_nodes;
        file >> num_nodes >> start >> stop; // Ð?c s? lu?ng node, node b?t d?u và node k?t thúc t? t?p
        
        // Ð?c heuristics c?a m?i node và luu vào b?ng hx
        for (int i = 0; i < num_nodes; ++i) {
            int h;
            string u;
            file >> u >> h;
            hx[u] = h;
        }

        // Ð?c các c?nh c?a m?i node và luu vào bi?n graph du?i d?ng danh sách k?
        for (int i = 0; i < num_nodes; ++i) {
            string u;
            int num_edges;
            file >> num_edges >> u;
            for (int j = 0; j < num_edges; ++j) {
                int k;
                string v;
                file >> v >> k;
                auto h = hx.find(v);
                if (h == hx.end()) { // Ki?m tra tính h?p l? c?a d? li?u
                    cout << "Loi du lieu:"; // N?u d? li?u không h?p l?, xu?t thông báo l?i và k?t thúc chuong trình
                    return;
                }
                graph[u].push_back({v,{k,h->second}});
            }
        }
        file.close(); // Ðóng t?p sau khi d?c xong
    }

    // Hàm tìm du?ng di t? node start d?n node goal và ghi k?t qu? vào t?p output
    vector<p1> search(string start, string goal, ofstream& output) {
        output << "|==========================================================================================================================================================|" << endl;
        output << setw(10) << "| Phat trien tran thai  | " << setw(34) << "            Trang thai ke :     k   ;   h    ;     g    ;    f          |" << setw(48) << "Danh Sach L\t\t   |" << endl;
        output << "|==========================================================================================================================================================|" << endl;

        // Kh?i t?o hàng d?i uu tiên d? l?a ch?n node ti?p theo cho vi?c m? r?ng
        priority_queue<Node, vector<Node>, CompareNode> pq;

        // Kh?i t?o b?ng chi phí t? di?m b?t d?u d?n m?i node
        map<string, int> g_cost;

        // Kh?i t?o b?ng luu tr? cha c?a m?i node trong quá trình tìm ki?m
        map<p1, p1> parent;

        // Ðua node b?t d?u vào hàng d?i uu tiên v?i các thông s? tuong ?ng
        pq.push(Node({start, 0}, 0, hx.find(start)->second, 0));

        // B?t d?u vòng l?p chính c?a thu?t toán A*
        while (!pq.empty()) {
            Node current = pq.top(); // L?y ra node có chi phí t?ng nh? nh?t t? hàng d?i uu tiên
            pq.pop(); // Lo?i b? node v?a l?y ra kh?i hàng d?i

            string strIn = ""; // Chu?i d? luu thông tin in ra
            strIn = current.id_f.first + " - " + to_string(current.id_f.second);
            output << "|\t" << left << strIn << "\t\t|";  // In thông tin v? node hi?n t?i

            // Ki?m tra xem node hi?n t?i có ph?i là node k?t thúc hay không
            if (current.id_f.first == goal) {
                output << "\t\t\t\t\t\t\t\t\t  " << left << setw(40) << "|" << "\t\t   |";

                // N?u là node k?t thúc, tr? v? du?ng di t? node b?t d?u d?n node k?t thúc
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

            // Duy?t qua các node k? c?a node hi?n t?i d? m? r?ng
            for (auto& neighbor : graph[current.id_f.first]) {
                string neighbor_id = neighbor.first;
                int k_cost = neighbor.second.first; // Chi phí th?c c?a c?nh t? node hi?n t?i d?n node k?
                int h_cost = neighbor.second.second; // Chi phí u?c lu?ng t? node k? d?n node k?t thúc
                int new_g_cost = current.g_cost + k_cost; // Chi phí m?i t? di?m b?t d?u d?n node k?
                int f_cost = new_g_cost + h_cost; // Chi phí t?ng (g_cost + h_cost)

                // T?o m?t node m?i v?i các thông s? dã tính toán
                Node t = Node({neighbor_id, f_cost}, new_g_cost, h_cost, k_cost);

                // Ðua node m?i vào hàng d?i uu tiên
                pq.push(t);

                // C?p nh?t thông tin v? cha c?a node k? trong quá trình tìm ki?m
                parent[{neighbor_id, f_cost}] = current.id_f;

                // Luu thông tin c?a node k? vào hàng d?i d? in ra sau này
                strIn = neighbor_id + " - " + to_string(k_cost) + " - " + to_string(h_cost) + " - " + to_string(new_g_cost) + " - " + to_string(f_cost) + " ; ";
                Ind.push(strIn);
            }

            // In thông tin v? node k? và các thông s? c?a nó
            output << "\t\t\t" << left << setw(50) << Ind.front()  << "|\t\t\t";
            Ind.pop(); // Lo?i b? node v?a in ra kh?i hàng d?i

            // In ra danh sách các node trong hàng d?i uu tiên
            output << setw(35) << strIn << "|\n";
            while (!Ind.empty()) {
                strIn = Ind.front();
                Ind.pop();
                output << "|\t\t\t|\t\t\t" << left << setw(50) << strIn << "|" << "\t\t\t\t\t\t\t   |";
                output << " \n";
            }
            output << "|----------------------------------------------------------------------------------------------------------------------------------------------------------|" << endl;
        }

        return vector<p1>(); // Tr? v? vector r?ng n?u không tìm th?y du?ng di
    }
};

// Hàm main
int main() {
    ofstream output("outputASao.txt"); // M? t?p output d? ghi k?t qu?
    AStar astar("graph.txt"); // T?o m?t d?i tu?ng AStar và d?c d? li?u t? t?p "graph.txt"
    vector<p1> path = astar.search(start, stop, output); // Tìm du?ng di t? node start d?n node stop và ghi k?t qu? vào t?p output

    output << "\n"; // Xu?ng dòng

    // Xu?t thông báo n?u không tìm th?y du?ng di ho?c xu?t du?ng di n?u tìm th?y
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

