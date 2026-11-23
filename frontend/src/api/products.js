const INVENTORY_URL = "http://localhost:8083";

export async function listarProdutos() {
    const resp = await fetch(`${INVENTORY_URL}/produtos`);
    if(!resp.ok) throw new Error("Falha ao carregar produtos");
    return resp.json();
}