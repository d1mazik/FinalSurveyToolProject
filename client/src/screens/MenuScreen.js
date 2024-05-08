import React from 'react';
import { Link } from 'react-router-dom';

function MenuScreen() {
    return (
        <div>
            <h1>Meny</h1>
            <Link to="/create-survey"><button>Skapa Survey</button></Link>
            <Link to="/create-question"><button>Skapa Fråga</button></Link>
            <Link to="/manage-questions"><button>Hantera Frågor</button></Link>
            <Link to="/view-responses"><button>Visa Svar</button></Link>
            <Link to="/admin"><button style={{ position: 'absolute', top: 0, right: 0 }}>Admin</button></Link>
            {/* Lägg till länk till sida för att visa alla frågor */}
            <Link to="/all-questions"><button>Hämta Alla Frågor</button></Link>
        </div>
    );
}

export default MenuScreen;
