import { Router } from "express";
import {
  getTasks,
  getTask,
  createTask,
  updateTask,
  deleteTask,
} from "../controllers/tasks.controller.js";
const router = Router();

router.get("/tasks",getTasks);
router.post("/task",createTask);
router.put("/task/:id",updateTask);
router.get("/task/:id",getTask);
router.delete("/task/:id",deleteTask);

export default router;
