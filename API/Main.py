import pyodbc
import flask
from Config import conn_str
from SQLQuery import *
# kết nối
try:
    conn = pyodbc.connect(conn_str)
    #methods: GET: select, POST: insert, PUT: update
    app = flask.Flask(__name__)
    # API getallbook
    @app.route('/book/getall', methods = ['GET'])
    def getAllBook():
        cursor = conn.cursor()
        sql = BookGetAll
        cursor.execute(sql) # thực thi
        results = [] # chưa kết quả trả về
        keys = []
        for i in cursor.description:
            keys.append(i[0]) # lấy keys
        print(keys)
        for i in cursor.fetchall():
            print(i)
            results.append(dict(zip(keys, i)))
        resp = flask.jsonify(results)
        resp.status_code = 200        
        return resp
    #get by id
    @app.route('/book/getbyid/<id>', methods = ['GET'])
    def getBookById(id=1):
        cursor = conn.cursor()
        sql = BookGetByID
        data = (id)
        cursor.execute(sql, data)
        result = {}
        keys = []
        for i in cursor.description:
            keys.append(i[0])
        for i in cursor.fetchall():
            result = (dict(zip(keys, i)))
        res = flask.jsonify(result)
        res.status_code = 200
        return res
    #API thêm mới một nhà cung cấp
    @app.route('/ncc/insert', methods = ['POST'])
    def insertNCC():
        try:
            ma = flask.request.json.get("mancc")
            ten = flask.request.json.get("tenncc")
            cursor = conn.cursor()
            sql = "insert into tNhaCungCap (MaNCC, TenNCC) values (?, ?)"
            data = (ma, ten)
            cursor.execute(sql, data)
            cursor.commit()
            res = flask.jsonify({"mess":"thành công"})
            res.status_code = 200
            return res
        except Exception as e:
            res = flask.jsonify({"mess":e})
            res.status_code = 200
            return res
    if __name__ == "__main__":
        app.run(port = 3333)
except Exception as e:
    print(e)
