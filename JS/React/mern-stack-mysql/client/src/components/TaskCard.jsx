import { useTasks } from "../context/TaskContext";
import { useNavigate } from "react-router-dom";

function TaskCard({ task }) {
  const { deleteTask,toggleTaskDone } = useTasks();
  const navigate = useNavigate();

  const handleDone = async () => {
    await toggleTaskDone(task.ID)
  };

  return (
    <div>
      <h2>{task.TITLE}</h2>
      <p>{task.DESCRIPTION}</p>
      <span>{task.DONE == 1 ? "✔️" : "❌"}</span>
      <button onClick={() => deleteTask(task.ID)}>Delete</button>
      <button onClick={() => navigate(`/edit/${task.ID}`)}>Edit</button>
      <button onClick={() => handleDone()}>Toggle Task </button>
    </div>
  );
}
export default TaskCard;
