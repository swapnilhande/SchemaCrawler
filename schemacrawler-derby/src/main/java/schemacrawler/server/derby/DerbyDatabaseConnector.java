/* 
 *
 * SchemaCrawler
 * http://sourceforge.net/projects/schemacrawler
 * Copyright (c) 2000-2014, Sualeh Fatehi.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 */
package schemacrawler.server.derby;


import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.options.DatabaseServerType;

public final class DerbyDatabaseConnector
  extends DatabaseConnector
{

  public DerbyDatabaseConnector()
  {
    super(new DatabaseServerType("derby", "Apache Derby"),
          "/help/Connections.derby.txt",
          "/schemacrawler-derby.config.properties",
          "/derby.information_schema");
  }

}