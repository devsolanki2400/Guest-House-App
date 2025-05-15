import React from 'react'; // Import React for JSX usage
import ReactDOM from 'react-dom/client'; // Import ReactDOM for rendering the app
import App from './App'; // Import the main App component
import './index.css'; // Import global styles for the app

// Create the root element and render the App component
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>  {/* StrictMode helps with highlighting potential issues in development */}
        <App />  {/* Render the main App component */}
    </React.StrictMode>
);
