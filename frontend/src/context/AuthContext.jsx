import { createContext, useContext, useState } from "react";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [token, setToken] = useState(null);
    const [usuario, setUsuario] = useState(null);

    function entrar(novoToken) {
        const payload = JSON.parse(atob(novoToken.split(".")[1]));
        setToken(novoToken);
        setUsuario({ nome: payload.nome, email: payload.email, id: payload.sub });
    }

    function sair() {
        setToken(null);
        setUsuario(null);
    }

    return(
        <AuthContext.Provider value={{token, usuario, entrar, sair }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}