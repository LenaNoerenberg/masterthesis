import React from 'react';
import './App.css';
import ListTestComponent from './components/ListTestComponent';
import ClientComponent from './components/ClientComponent';
import Navbar from './navigation/Navigation';
import { BrowserRouter, Route, Routes} from 'react-router-dom';

function App() {
  return (
    <div className='App'>
      <h1>Welcome!</h1>
      {/* <Navbar/> */}
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<ClientComponent/>}/>
          <Route path='/test' element={<ListTestComponent/>}/>
        </Routes>
      </BrowserRouter>
    </div>
    
  );
}

export default App;
