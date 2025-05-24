package RTDRestaurant.View.Swing;

import RTDRestaurant.Model.ModelMenu;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class WrapLayout extends FlowLayout
{
    private Dimension preferredLayoutSize;

    public WrapLayout()
    {
        super();
    }

    public WrapLayout(int align)
    {
        super(align);
    }

    public WrapLayout(int align, int hgap, int vgap)
    {
        super(align, hgap, vgap);
    }

    @Override
    public Dimension preferredLayoutSize(Container target)
    {
        return layoutSize(target, true);
    }

    @Override
    public Dimension minimumLayoutSize(Container target)
    {
        Dimension minimum = layoutSize(target, false);
        minimum.width -= (getHgap() + 1);
        return minimum;
    }

    private Dimension layoutSize(Container target, boolean preferred)
    {
        synchronized (target.getTreeLock())
        {
            int targetWidth = target.getSize().width;

            if (targetWidth == 0)
                targetWidth = Integer.MAX_VALUE;

            int hgap = getHgap();
            int vgap = getVgap();
            Insets insets = target.getInsets();
            int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
            int maxWidth = targetWidth - horizontalInsetsAndGap;

            Dimension dim = new Dimension(0, 0);
            int rowWidth = 0;
            int rowHeight = 0;

            int nmembers = target.getComponentCount();

            for (int i = 0; i < nmembers; i++)
            {
                Component m = target.getComponent(i);

                if (m.isVisible())
                {
                    Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

                    if (rowWidth + d.width > maxWidth)
                    {
                        addRow(dim, rowWidth, rowHeight);
                        rowWidth = 0;
                        rowHeight = 0;
                    }

                    if (rowWidth != 0)
                    {
                        rowWidth += hgap;
                    }

                    rowWidth += d.width;
                    rowHeight = Math.max(rowHeight, d.height);
                }
            }

            addRow(dim, rowWidth, rowHeight);

            dim.width += horizontalInsetsAndGap;
            dim.height += insets.top + insets.bottom + vgap * 2;

            Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);
            if (scrollPane != null)
            {
                dim.width -= (hgap + 1);
            }

            return dim;
        }
    }

    private void addRow(Dimension dim, int rowWidth, int rowHeight)
    {
        dim.width = Math.max(dim.width, rowWidth);

        if (dim.height > 0)
        {
            dim.height += getVgap();
        }

        dim.height += rowHeight;
    }

    public static class MenuItem extends javax.swing.JPanel {

        public void setAlpha(float alpha) {
            this.alpha = alpha;
        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public EventMenuSelected getEventSelected() {
            return eventSelected;
        }

        public void setEventSelected(EventMenuSelected eventSelected) {
            this.eventSelected = eventSelected;
        }

        public int getIndex() {
            return index;
        }

        public ModelMenu getMenu() {
            return menu;
        }

        private float alpha;
        private ModelMenu menu;
        private boolean open;
        private EventMenuSelected eventSelected;
        private int index;

        /*
            Khởi tạo MenuItem với cái tham số ModelMenu, event mousePress, event chọn Menu và index của Menu đó
         */
        public MenuItem(ModelMenu menu, EventMenu event, EventMenuSelected eventSelected, int index) {
            initComponents();
            this.menu = menu;
            this.eventSelected = eventSelected;
            this.index = index;
            setOpaque(false);
            setLayout(new MigLayout("wrap, fillx, insets 0", "[fill]", "[fill, 40!]0[fill, 35!]"));
            //Menu cha
            MenuButton firstItem = new MenuButton(menu.getIcon(), "       " + menu.getMenuName());
            firstItem.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(menu.getSubMenu().length>0){
                        if(event.menuPress(MenuItem.this, !open)){
                            open=!open;
                        }
                    }
                    try {
                        eventSelected.menuSelected(index,-1);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            add(firstItem);
            //Add Menu con
            int subMenuIndex = -1;
            for (String st : menu.getSubMenu()) {
                MenuButton item = new MenuButton(st);
                item.setIndex(++subMenuIndex);
                //Add Event khi được chọn cho subMenu
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            eventSelected.menuSelected(index, item.getIndex());
                        } catch (SQLException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                });
                add(item);
            }

        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 400, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 300, Short.MAX_VALUE)
            );
        }// </editor-fold>//GEN-END:initComponents
        @Override
        public void paintComponent(Graphics g) {
            int width = getWidth();
            int height = getPreferredSize().height;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(46, 20, 55, 70));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fillRect(0, 2, width, 38);
            g2.setComposite(AlphaComposite.SrcOver);
            g2.fillRect(0, 40, width, height - 40);
            g2.setColor(new Color(107, 107, 131));
            g2.drawLine(30, 40, 30, height - 17);
            for (int i = 0; i < menu.getSubMenu().length; i++) {
                int y = ((i + 1) * 35 + 40) - 17;
                g2.drawLine(30, y, 38, y);
            }

            if (menu.getSubMenu().length > 0) {
                createArrowButton(g2);
            }
            super.paintComponent(g);
        }

        public void createArrowButton(Graphics2D g2) {
            int size = 4;
            int y = 20;
            int x = 235;
            g2.setColor(new Color(230, 230, 230));
            float ay = alpha * size;
            float ay1 = (1f - alpha) * size;
            g2.drawLine(x, (int) (y + ay), x + 4, (int) (y + ay1));
            g2.drawLine(x + 4, (int) (y + ay1), x + 8, (int) (y + ay));
        }
        // Variables declaration - do not modify//GEN-BEGIN:variables
        // End of variables declaration//GEN-END:variables
    }
}