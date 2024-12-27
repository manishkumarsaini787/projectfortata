import React from "react";
import ReactDOM from "react-dom/client";
import App from "./Components/App";  // Ensure the path is correct
import './index.css';  // Optional if you have global styles

const rootElement = document.getElementById("root");
const root = ReactDOM.createRoot(rootElement);

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
