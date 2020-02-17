async function removeProduct(id) {
    try {
        // make the DELETE request
        let response = await fetch('?id=' + id, {
            method : 'DELETE'
        });

        // get the row that was deleted
        let rowId = 'product-' + id;
        let row = document.getElementById(rowId);

        // remove the row from the page 
        row.remove();
    } catch (e) {
        console.error(e);
        alert('An error occured. Please check the consoles of the browser and the backend.');
    }
}