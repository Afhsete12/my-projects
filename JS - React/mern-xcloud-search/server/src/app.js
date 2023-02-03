import  express  from "express"
import {pool} from "./db.js"

const app = express()
app.get("/ping", async (req,res)=>{
    const [result] = await pool.query("SELECT 'Hello World' as RESULT")
    console.log(result[0])
})
app.get("/create", async (req,res)=>{
    const result = await pool.query("INSERT INTO games(name,expirationDate,startDate) VALUES('Infernax','2000-05-05','')")
    res.json(result)
})
app.get("/", async (req,res)=>{
    const [rows] = await pool.query("SELECT * FROM games")
    res.json(rows)
})

app.listen(3000)
//(console.log(`Server on port 3000`)
