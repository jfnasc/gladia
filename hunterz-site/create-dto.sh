#!/bin/bash

cat << EOF
<?php
if (! defined('BASEPATH')) exit('No direct script access allowed');

class $1
{
EOF

for col in $(cat colunas.lst);do
cat << EOF
  private \$$col = null;
EOF
done

echo ""

for col in $(cat colunas.lst);do
cat << EOF
  public function get_$col ()
  {
      return \$this->$col;
  }

  public function set_$col (\$$col)
  {
      \$this->$col = \$$col;
  }

EOF
done
cat << EOF

}
?>
EOF


for col in $(cat colunas.lst);do
cat << EOF
  \$dto->set_$col(\$row->$col);
EOF
done
