/* 
 *
 * SchemaCrawler
 * http://sourceforge.net/projects/schemacrawler
 * Copyright (c) 2000-2010, Sualeh Fatehi.
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

package schemacrawler.tools.integration.graph;


import java.io.File;
import java.sql.Connection;

import schemacrawler.schema.Database;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.tools.analysis.DatabaseWithWeakAssociations;
import schemacrawler.tools.executable.BaseExecutable;

/**
 * Main executor for the graphing integration.
 * 
 * @author Sualeh Fatehi
 */
public final class GraphExecutable
  extends BaseExecutable
{

  enum GraphCommandType
  {
    graph, detailed_graph
  }

  private final GraphCommandType graphCommandType;

  public GraphExecutable()
  {
    this(null);
  }

  public GraphExecutable(final String command)
  {
    super(command);
    if (command == null)
    {
      graphCommandType = GraphCommandType.graph;
    }
    else
    {
      graphCommandType = GraphCommandType.valueOf(command);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void executeOn(final Database database, final Connection connection)
    throws Exception
  {
    // Create dot file
    final DatabaseWithWeakAssociations analyzedDatabase;
    if (database instanceof DatabaseWithWeakAssociations)
    {
      analyzedDatabase = (DatabaseWithWeakAssociations) database;
    }
    else
    {
      analyzedDatabase = new DatabaseWithWeakAssociations(database);
    }
    final File dotFile = File.createTempFile("schemacrawler.", ".dot");
    final DotWriter dotWriter = new DotWriter(dotFile);
    dotWriter.open();
    dotWriter.print(database.getSchemaCrawlerInfo(),
                    database.getDatabaseInfo(),
                    database.getJdbcDriverInfo());
    for (final Schema schema: database.getSchemas())
    {
      for (final Table table: schema.getTables())
      {
        dotWriter.print(table);
        dotWriter
          .print(DatabaseWithWeakAssociations.getWeakAssociations(table));
      }
    }
    dotWriter.close();

    // Create graph image
    final GraphGenerator dot = new GraphGenerator(dotFile);
    dot.setGraphOutputFormat(outputOptions.getOutputFormatValue());
    dot.setDiagramFile(outputOptions.getOutputFile());
    dot.generateDiagram();
  }
}
