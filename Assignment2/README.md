# Cafe POS (INFO 5100 Assignment 2)

A Java Swing point-of-sale & customer management system for a coffee shop.

## Open & Run in NetBeans
1. **File → Open Project…** and select this folder (it's a Maven project).
2. Right–click the project → **Run** (or `Clean and Build` first).
3. The main window opens with a Dashboard using CardLayout.

## Features Implemented (per assignment)
- Product CRUD (add, edit, delete) with ProductCatalog
- Customer & Order creation in one flow; one active order per customer
- Search Customers by **ID** or **Name** (returns multiple for same name)
- View/Update customer and order details from search results
- Delete a Customer record from the table
- List Orders table: select a row → View or Delete
- **CardLayout** workflows across panels
- **Validations** with dialogs; required fields enforced; uses at least 4 primitive types
- **Initial Data** (5+ products, 3+ orders, duplicate product names for search tests)
- Simple, clean UI with basic colors and alignment

## Package Structure
- `com.cafe.model` — `Product`, `Customer`, `Order`, enums
- `com.cafe.directory` — `ProductCatalog`, `CustomerDirectory`, `OrderDirectory`
- `com.cafe.ui` — Swing UI (`Main`, `MainFrame`, panels)
- `com.cafe.util` — `Validators`

## Notes
- Git requirements (branch/10+ commits/screenshots) must be done in your own repo.
