context('Assertions', () => {
    beforeEach(() => {
        cy.visit('http://localhost:8080/project/1/tasks');
    });

    it('Should open and close task card', () => {
        cy.get('.card-title').first().click();
        cy.wait(1000);
        cy.get('.modal-dialog').should('be.visible');
        cy.wait(1000);
        cy.get('app-expanded-task-card > .modal > .modal-dialog > .modal-content >.modal-header > .close').first().click();
        cy.wait(1000);
        cy.get('.modal-dialog').should('not.be.visible');
    });

    it('Should open and close task creation', () => {
        cy.get('#newbuttontask').first().click();
        cy.wait(1000);
        cy.get('form').should('be.visible');
        cy.wait(1000);
        cy.get('#newTask > .modal-dialog > .modal-content >.modal-header > .close').first().click();
        cy.wait(1000);
        cy.get('form').should('not.be.visible');
    });

    it('Should change the name of a task', () => {
        // Open card
        cy.get('.card-title').first().click();
        cy.wait(1000);
        cy.get('.modal-dialog').should('be.visible');
        cy.wait(1000);

        // Click modify
        cy.get('button').contains("Modifier").click();
        cy.wait(1000);

        // Enter new task name
        let text = Date.now();
        cy.get('.modify > form > div > input[formcontrolname="title"]').first().clear();
        cy.get('.modify > form > div > input[formcontrolname="title"]').first().type(text);

        // Apply
        cy.get('.modify > form > button[type="submit"]').first().click();
        cy.wait(1000);

        cy.get('.modal-dialog').should('not.be.visible');
        cy.get('.card-title').first().should(($e) => {
            expect($e).to.contain(text);
        });
    });

    it('Should create a new task', () => {
        // Open task form
        cy.get('#newbuttontask').first().click();
        cy.wait(1000);
        cy.get('form').should('be.visible');
        cy.wait(1000);

        let seed = Date.now();

        let taskCreate = {
            title: "task "+seed,
            duration: seed/2 + " hh",
        }

        // Fill the form and send
        cy.get('#newTask > .modal-dialog > .modal-content > .modal-body > form > div > input[formcontrolname="title"]')
            .first().type(taskCreate.title);
        cy.get('#newTask > .modal-dialog > .modal-content > .modal-body > form > div > input[formcontrolname="duration"]')
            .first().type(taskCreate.duration);
        cy.get('#newTask > .modal-dialog > .modal-content > .modal-body > form > button[type="submit"]')
            .first().click();

        cy.wait(1000);

        // Close form
        cy.get('#newTask > .modal-dialog > .modal-content >.modal-header > .close').first().click();
        cy.wait(1000);
        cy.get('form').should('not.be.visible');
        
        cy.wait(1000);

        cy.get('.card-title').contains(taskCreate.title).should('be.visible');
    });

});
