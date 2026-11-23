const ORDER_URL = "http://localhost:8081";

function headersAutenticados(token){
    return { Authorization: `Bearer ${token}` };
}

export async function carregarPedidos(token){
    const resp = await fetch(`${ORDER_URL}/orders`, { headers: headersAutenticados(token) });
    if (!resp.ok) throw new Error("Falha ao carregar pedidos");
    return resp.json();
}

export async function criarPedido(token, produtoId, quantidade){
    const resp = await fetch(`${ORDER_URL}/orders`, {
        method: "POST",
        headers: headersAutenticados(token),
        body: JSON.stringify({ produtoId, quantidade}),
    });
    if(!resp.ok) throw new Error(await resp.text());
    return resp.json();
}

export const ORDERS_STREAM_URL = `${ORDER_URL}/orders/stream`;