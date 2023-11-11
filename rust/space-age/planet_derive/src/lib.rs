use proc_macro::TokenStream;
use quote::quote;
use syn::{self};

#[proc_macro_derive(Planet, attributes(earth_years))]
pub fn planet_derive(input: TokenStream) -> TokenStream {
    // Construct a representation of Rust code as a syntax tree
    // that we can manipulate
    let ast = syn::parse(input).unwrap();

    // Build the trait implementation
    impl_planet(&ast)
}

fn impl_planet(ast: &syn::DeriveInput) -> TokenStream {
    let name = &ast.ident;
    let earth_years: f64 = ast.attrs.iter()
        .find(|a| a.meta.path().is_ident("earth_years"))
        .map_or_else(|| 1.0, |a| get_earth_years(a).unwrap());

    let gen = quote! {
        #[automatically_derived]
        impl Planet for #name {
            fn years_during(d: &Duration) -> f64 {
                d.years() / #earth_years
            }
        }
    };
    gen.into()
}

fn get_earth_years(attr: &syn::Attribute) -> syn::Result<f64> {
    let mnv = attr.meta.require_name_value()?;
    if let syn::Expr::Lit(lit) = &mnv.value {
        if let syn::Lit::Float(f) = &lit.lit {
            return Ok(f.base10_parse()?)
        }
    }

    let message = "expected #[earth_years = <f64>]";
    Err(syn::Error::new_spanned(attr, message))
}
