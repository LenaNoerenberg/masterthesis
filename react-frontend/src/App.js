import React from 'react';
import './App.css';
import ListTestComponent from './components/ListTestComponent';
import ReportComponent from './components/ReportComponent';
import Navbar from './navigation/Navigation';
import { BrowserRouter, Route, Routes} from 'react-router-dom';

/**
* Home Component
* Von hier aus werden die Routs definiert
*/
function App() {
  return (
    <div className='App'>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<ListTestComponent/>}/>
          <Route path='/report' element={<ReportComponent/>}/>
        </Routes>
      </BrowserRouter>
    </div>
    
  );
}

export default App;
