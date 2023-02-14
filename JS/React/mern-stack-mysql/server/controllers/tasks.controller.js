import { pool } from "../db.js";

export const getTasks = async (req, res) => {
  try {
    const [result] = await pool.query("SELECT * FROM TASKS");
    res.json(result);
  } catch (error) {
    return res.status(500).json({ message: error.message });
  }
};

export const getTask = async (req, res) => {
  const [result] = await pool.query("SELECT * FROM TASKS WHERE ID = ?", [
    req.params.id,
  ]);

  if (result.length == 0)
    return res.status(404).json({ message: "Task not found" });

  res.json(result[0]);
};

export const createTask = async (req, res) => {
  const { title, description } = req.body;
  const [result] = await pool.query(
    "INSERT INTO TASKS (TITLE,DESCRIPTION) VALUES (?,?)",
    [title, description]
  );
  res.json({
    id: result.insertId,
    title,
    description,
  });
};

export const updateTask = async (req, res) => {
  const result = await pool.query("UPDATE TASKS SET ? WHERE ID = ?", [
    req.body,
    req.params.id,
  ]);
  res.json(result);
};

export const deleteTask = async (req, res) => {
  const [result] = await pool.query("DELETE FROM TASKS WHERE ID = ?", [
    req.params.id,
  ]);

  if (result.affectedRows == 0)
    return res.status(404).json({ message: "Task not found" });

  return res.sendStatus(204);
};
