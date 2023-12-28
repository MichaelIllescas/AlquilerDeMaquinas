package com.example.alquilerdemaquinas;

import com.example.alquilerdemaquinas.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Factura extends Activity {

	TextView t1,t2,t3,t4,t5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_factura);
		

		t1= (TextView)findViewById(R.id.NombreFactura);
		t2= (TextView)findViewById(R.id.telefonoFactura);
		t3= (TextView)findViewById(R.id.cantidadDiasfactura);
		t4= (TextView)findViewById(R.id.ImporteFactura);
		
		Bundle b= getIntent().getExtras();
		String nombre= b.getString("nombre");
		String telefono= b.getString("telefono");
		String dias= b.getString("dias");
		String importe= b.getString("importe");
		
		t1.setText(nombre);
		t2.setText(telefono);
		t3.setText(dias);
		t4.setText(importe);
				
		
	}
	
	public void salir(View v)
	{
		finish();
		
	}

}
