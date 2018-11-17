/**
*
* Copyright (C) 2011  Gilnei Marcos Risso All Rights Reserved.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>
* 
* You can contact the software author by e-mail gilnei.risso@gmail.com
*
*/

package br.com.autoStock.strategy;

import java.util.ArrayList;
import com.mf4j.Quote;
import br.com.autoStock.util.entity.Configuration;

@SuppressWarnings("unused")
public abstract class Regras {
		
	public int getCandleInicial() {		
		return 0;
	}
	
	public abstract Configuration getConfiguration();
	
	public abstract void init(Configuration conf, Quote[] dado);
	
	public abstract float regraCompra(Quote historico, int dataAtual);
	
	public abstract float regraVenda(Quote historico, int dataAtual);

}
