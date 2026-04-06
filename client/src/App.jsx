import { useState, useEffect } from 'react'
import './App.css'

const API = '/api/todos'

function App() {
  const [todos, setTodos] = useState([])
  const [text, setText] = useState('')

  useEffect(() => {
    fetch(API).then(r => r.json()).then(setTodos)
  }, [])

  const addTodo = async (e) => {
    e.preventDefault()
    if (!text.trim()) return
    const res = await fetch(API, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ text }),
    })
    const todo = await res.json()
    setTodos([...todos, todo])
    setText('')
  }

  const toggleTodo = async (id, completed) => {
    const res = await fetch(`${API}/${id}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ completed: !completed }),
    })
    const updated = await res.json()
    setTodos(todos.map(t => (t.id === id ? updated : t)))
  }

  const deleteTodo = async (id) => {
    await fetch(`${API}/${id}`, { method: 'DELETE' })
    setTodos(todos.filter(t => t.id !== id))
  }

  return (
    <div className="app">
      <h1>Todo App</h1>
      {todos.length > 0 && (
        <p className="todo-count">
          {todos.filter(t => !t.completed).length} remaining &middot; {todos.filter(t => t.completed).length} completed
        </p>
      )}
      <form onSubmit={addTodo} className="todo-form">
        <input
          value={text}
          onChange={(e) => setText(e.target.value)}
          placeholder="What needs to be done?"
          autoFocus
        />
        <button type="submit">Add</button>
      </form>
      <ul className="todo-list">
        {todos.map((todo) => (
          <li key={todo.id} className={todo.completed ? 'completed' : ''}>
            <span onClick={() => toggleTodo(todo.id, todo.completed)}>
              {todo.completed ? '\u2713' : '\u25CB'} {todo.text}
            </span>
            <button onClick={() => deleteTodo(todo.id)} className="delete">
              Delete
            </button>
          </li>
        ))}
      </ul>
      {todos.length === 0 && <p className="empty">No todos yet. Add one above!</p>}
    </div>
  )
}

export default App
